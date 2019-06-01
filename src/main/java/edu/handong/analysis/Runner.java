package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.Utils;


public class Runner {
	
	static String input;
	static String output;
	static String analysis;
	static String coursecode;
	static String startyear;
	static String endyear; 
	static boolean help;
	
	public static void runner(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			ArrayList<CSVRecord> lines = Utils.getLines(input, true);
			
			
			
			if(analysis.equals("2")) {
				if(coursecode != null) {
					HashMap<String,Student> yearAndSemester = HGUCoursePatternAnalyzer.loadStudentYearAndSemester(lines, Integer.parseInt(startyear), Integer.parseInt(endyear));
					Map<String, Student> sortedYearsAndSemester = new TreeMap<String,Student>(yearAndSemester);
					ArrayList<String> yearAndSemesterToSaved = HGUCoursePatternAnalyzer.countStudentsOfCoursesTaken(sortedYearsAndSemester, coursecode);
					Utils.writeASecondFile(yearAndSemesterToSaved, output);
				}
				else {
					printHelp(options);
					return;
				}
			}
			else if(analysis.equals("1")) {
				HashMap<String,Student> students = HGUCoursePatternAnalyzer.loadStudentCourseRecords(lines, Integer.parseInt(startyear), Integer.parseInt(endyear));
				Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);
				ArrayList<String> linesToBeSaved = HGUCoursePatternAnalyzer.countNumberOfCoursesTakenInEachSemester(sortedStudents);
				Utils.writeAFile(linesToBeSaved, output);
			}
					
		}					
	}

	private static boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			coursecode = cmd.getOptionValue("c");
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private static Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output  path")
				.required()
				.build());

		options.addOption(Option.builder("a").longOpt("anlysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for 'a-2' option")
				.hasArg()
				.argName("course code")
				//.required()
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.argName("Help")
				.build());
				

		return options;
	}
	
	private static void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

}
