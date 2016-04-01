package com.android.lehuitong.model;

import android.content.Context;
import android.widget.ProgressBar;

public class GetRankProgressUtil {
	private static int GRIDE_ONE=200;
	private static int GRIDE_TWO=500;
	private static int GRIDE_THREE=1200;
	private static int GRIDE_FOUR=2300;
	private static int GRIDE_FIVE=5000;
	private static int GRIDE_SIX=11000;
	private static int GRIDE_SEVEN=25000;
	private static int GRIDE_EIGHT=52000;
	private static int GRIDE_NINE=100000;
	private static int GRIDE_TEN=100000000;
	public static void GetRankProgress(int grade,int currentRank,int maxRank,ProgressBar bar){
		
		if(grade==1){
			bar.setMax(maxRank);
			bar.setProgress(currentRank);
		}else if(grade==2){
			bar.setMax(maxRank-GRIDE_ONE);
			bar.setProgress(currentRank-GRIDE_ONE);
		}else if(grade==3){
			bar.setMax(maxRank-GRIDE_TWO);
			bar.setProgress(currentRank-GRIDE_TWO);
		}else if(grade==4){
			bar.setMax(maxRank-GRIDE_THREE);
			bar.setProgress(currentRank-GRIDE_THREE);
		}else if(grade==5){
			bar.setMax(maxRank-GRIDE_FOUR);
			bar.setProgress(currentRank-GRIDE_FOUR);
		}else if(grade==6){
			bar.setMax(maxRank-GRIDE_FIVE);
			bar.setProgress(currentRank-GRIDE_FIVE);
		}else if(grade==7){
			bar.setMax(maxRank-GRIDE_SIX);
			bar.setProgress(currentRank-GRIDE_SIX);
		}else if(grade==8){
			bar.setMax(maxRank-GRIDE_SEVEN);
			bar.setProgress(currentRank-GRIDE_SEVEN);
		}else if(grade==9){
			bar.setMax(maxRank-GRIDE_EIGHT);
			bar.setProgress(currentRank-GRIDE_EIGHT);
		}else if(grade==10){
			bar.setMax(maxRank-GRIDE_NINE);
			bar.setProgress(currentRank-GRIDE_NINE);
		}
	}
}
