import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Nilay_Yucel_2017510082 {

	public int DP2(double[][] investment, int[] demand, int B, int t) {

		double income = 0,previous = 0,total = 0,max_profit = 0;
		double[][] DP = new double[investment.length][investment[0].length];
		double[] total_income = new double[demand.length];

		for (int i = 0; i < demand.length; i++) {//to find monthly total income
			income = (((double) B) * demand[i]);
			total_income[i] = ((income / 2) + previous);
			previous = income / 2;
		}

		for (int i = 0; i < DP.length; i++) {

			for (int j = 0; j < DP[i].length; j++) {

				if (i == 0) {
					total = (total_income[i] + (total_income[i] * (investment[i][j] / 100)));
					DP[i][j] = (int) total;
				} else {

					max_profit = 0;
					for (int p = 0; p < DP[i - 1].length; p++) {
						if (j == p) {
							total = (DP[i - 1][p] + total_income[i]);
							total += (total * investment[i][j] / 100);

						} else {
							total = DP[i - 1][p] - (DP[i - 1][p] * ((double) t) / 100) + total_income[i];
							total += (total * investment[i][j] / 100);

						}

						if (total > max_profit) {
							max_profit = total;
						}

					}
					DP[i][j] = (int) max_profit;
				}
			}
			if (i == DP.length - 1) {
				for (int j = 0; j < DP[i].length; j++) {
					total = DP[i][j];
					if (total > max_profit) {
						max_profit = total;
					}
				}
			}
		}

		return (int) max_profit;
	}

	public int DP1(int[] garage_cost, int[] demand, int p, int d) {

		int expense = 0,min_expense = Integer.MAX_VALUE;
		int[][] DP = new int[demand.length + 1][garage_cost.length];

		for (int i = 0; i < DP.length; i++) {
			for (int j = 0; j < DP[0].length; j++) {
				min_expense = Integer.MAX_VALUE;
				if (i == 0) {
					if (j == 0)
						DP[i][j] = 0;
					else
						DP[i][j] = garage_cost[j - 1];
				} else {
					for (int k = 0; k < DP[0].length; k++) {

						if (demand[i - 1] - p + j - k > 0) {

							expense = ((demand[i - 1] - p + j - k) * d) + DP[0][j] + DP[i - 1][k];

						} else {
							expense = DP[0][j] + DP[i - 1][k];
						}

						if (expense < min_expense) {
							min_expense = expense;
						}
					}
					DP[i][j] = min_expense;
				}

			}

			if (i == DP.length - 1) {//to find the smallest of the last line
				for (int j = 0; j < DP[i].length; j++) {
					expense = DP[i][j];
					if (expense < min_expense) {
						min_expense = expense;
					}

				}
			}
		}

		return min_expense;
	}

	public int Greedy2(double[][] investment, int[] demand, int B, int t) {

		double income = 0,previous = 0,total = 0,max_profit = 0;
		int selected = 0, selected_previous = 0;
		double[][] Greedy = new double[investment.length][investment[0].length];
		double[] total_income = new double[demand.length];

		for (int i = 0; i < demand.length; i++) {//to find monthly total income
			income = (((double) B) * demand[i]);
			total_income[i] = ((income / 2) + previous);
			previous = income / 2;
		}

		for (int i = 0; i < Greedy.length; i++) {

			max_profit = 0;
			selected = 0;
			for (int j = 0; j < Greedy[i].length; j++) {

				if (i == 0) {
					total = (total_income[i] + (total_income[i] * (investment[i][j] / 100)));
					Greedy[i][j] = (int) total;

				} else {

					if (j == selected_previous) {
						total = (Greedy[i - 1][selected_previous] + total_income[i]);
						total += (total * investment[i][j] / 100);

					} else {
						total = Greedy[i - 1][selected_previous]
								- (Greedy[i - 1][selected_previous] * ((double) t) / 100) + total_income[i];
						total += (total * investment[i][j] / 100);

					}

					Greedy[i][j] = (int) total;
				}

				if (total > max_profit) {
					max_profit = total;
					selected = j;
				}
			}
			selected_previous = selected;

			if (i == Greedy.length - 1) {
				for (int j = 0; j < Greedy[i].length; j++) {
					total = Greedy[i][j];
					if (total > max_profit) {
						max_profit = total;
					}
				}
			}

		}

		return (int) max_profit;
	}

	public int Greedy1(int[] garage_cost, int[] demand, int p, int d) {

		int expense = 0,min_expense = Integer.MAX_VALUE;
		int[] garage = new int[garage_cost.length + 1];
		int[] Greedy = new int[demand.length];

		for (int i = 0; i < Greedy.length; i++) {
			if (i == 0) {

				for (int k = 0; k < garage.length; k++) {//For 0.month

					if (k == 0) {
						garage[k] = 0;
					} else {
						garage[k] = garage_cost[k - 1];
					}
					
					if (demand[i] - p - k >= 0) {
						expense = ((demand[i] - p - k) * d) + garage[k];

					} else {
						expense = garage_cost[k + p - demand[i]] + garage[k];
					}

					if (expense < min_expense) {
						min_expense = expense;
					}
				}
				Greedy[i] = min_expense;

			} else {

				if (demand[i] - p  >= 0) {

					expense = ((demand[i] - p ) * d) + Greedy[i - 1];

				} else {
					expense = Greedy[i - 1];
				}

				Greedy[i] = expense;
			}
		}
		return Greedy[Greedy.length - 1];

	}

	public static void main(String[] args) throws IOException {

		int p = 5, d = 5, x = 30, t = 3, B = 150, c = 5;
		int last_month = 0;

		// Read File

		
		double[][] investment = new double[x][c];//I created a array according to month and investment number
		String[] split;
		FileReader fileReader = new FileReader("investment.txt");

		BufferedReader br = new BufferedReader(fileReader);
		String line = br.readLine();

		for (int i = 0; i < investment.length; i++) {
			line = br.readLine();//I didn't add the first line to txt
			split = line.split("	");
			for (int j = 1; j < investment[i].length + 1; j++) {
				investment[i][j - 1] = Integer.valueOf(split[j]); //I didn't add the first column to txt
			}
		}

		br.close();

		int[] demand = new int[x];//I created a array according to month number
		fileReader = new FileReader("month_demand.txt");

		br = new BufferedReader(fileReader);
		line = br.readLine();
		for (int i = 0; i < demand.length; i++) {
			line = br.readLine();//I didn't add the first line to txt
			split = line.split("	");
			demand[i] = Integer.valueOf(split[1]);
			if (i == demand.length - 1) {
				last_month = (Integer.valueOf(split[1]) * B) / 2; //for last month income
			}
		}

		br.close();

		int total_demand = 0;
		for (int i = 0; i < demand.length; i++) {
			total_demand += demand[i];
		}

		int[] garage_cost = new int[total_demand];//I created a array according to total demand
		fileReader = new FileReader("garage_cost.txt");

		br = new BufferedReader(fileReader);
		line = br.readLine();
		for (int i = 0; i < garage_cost.length; i++) {
			line = br.readLine();//I didn't add the first line to txt
			split = line.split("	");
			garage_cost[i] = Integer.valueOf(split[1]);//I didn't add the first column to txt
		}

		br.close();

		Nilay_Yucel_2017510082 ny = new Nilay_Yucel_2017510082();

		System.out.println("DP Result - Profit: " + ny.DP2(investment, demand, B, t) + "+" + last_month);
		System.out.println("Greedy Result - Profit: " + ny.Greedy2(investment, demand, B, t) + "+" + last_month+"\n");
		System.out.println("DP Results - Cost: " + ny.DP1(garage_cost, demand, p, d));
		System.out.println("Greedy Result - Cost: " + ny.Greedy1(garage_cost, demand, p, d));

	}

}
