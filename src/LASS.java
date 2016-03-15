import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Arrays;
public class LASS {
	public static double scoringMatrix[][] = new double[128][128];
	public static String searchPattern; // pattern which we need to find
	public static void main(String[] args) throws UnsupportedEncodingException {
		/*
		 * first argument is file/database to search in (args[0)
		 * second argument is pattern which we have to find (args[1)
		 * third argument is file of scoring matrix (args[2)
		 */
		ImportScoringMatrix(args[2]);
		String Seeds[] = new String[args[1].length()-1];
		searchPattern = args[1];
		Seeds = createSeeds(searchPattern, 3);
	}
	private static String[] createSeeds(String pattern, int seedLength) {
		String seeds[] = new String[pattern.length()-seedLength+1];
		char seed[] = new char[seedLength];
		for(int i=0;i<pattern.length()-seedLength;i++){
			for(int j=i;j<seedLength;j++){
				seed[j-i] = pattern.charAt(j);
			}
			seeds[i] = String.copyValueOf(seed);
		}
		return seeds;
	}
	private static void ImportScoringMatrix(String filename) {
		try {
			Scanner scanner = new Scanner(new FileReader(filename));
			/* build scoring matrix from text file */
			for(int i=0;i<128;i++){
				for(int j=0;j<128;j++){
					scoringMatrix[i][j] = scanner.nextDouble();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}
	private static double ScoreOfEachSeed(String seed){
        int k=seed.length();
        double score=0;
		for(int p=0;p<k;p++){
            score=score+scoringMatrix[(int)seed.charAt(p)][(int)seed.charAt(p+1)];
        }
        return score;
	}
	private static String leastscoringseeds(String lseed,int threshold){
		if(ScoreOfEachSeed(lseed)>threshold){
			return lseed;
		}
		else
			return "";
	}
	private static void exactmatchoflss(String filename,String lsseed,double seedscore){
		try {
			Scanner scanner = new Scanner(new FileReader(filename));
			/* build scoring matrix from text file */
			while (scanner.hasNextLine()) {
				String columns = scanner.nextLine();
				int s=lsseed.length();
				double currentscore=0;
				int i;
				/*
				 * seed length traversal should happen for all characters in this line.
				 */
					for (i=1;i<s;i++)
					{
						currentscore=currentscore+scoringMatrix[(int)columns.charAt(i-1)][(int)columns.charAt(i)];
					}
					if(currentscore==seedscore){
						/* 
						 * Score matched, now we can extend left and right to make a EXACT match
						 */
						
					}
					else{
						currentscore=currentscore-scoringMatrix[(int)columns.charAt(i-s)][(int)columns.charAt(i-s+1)];
					}

			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}
