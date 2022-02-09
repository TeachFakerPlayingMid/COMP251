import java.text.DecimalFormat;
import java.util.*;

class Assignment implements Comparator<Assignment>
{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2)
	{
		// TODO Implement this
		//DecimalFormat df = new DecimalFormat("0.0000");

		//System.out.println(df.format((double)1/2));
		double a1_weight = (double) a1.weight;
		double a1_completiontime = (double) a1.completiontime;

		double a2_weight = (double) a2.weight;
		double a2_completiontime = (double) a2.completiontime;

		double A1_weight_per_hour = a1_weight/a1_completiontime;
		double A2_weight_per_hour = a2_weight/a2_completiontime;

		if (A1_weight_per_hour > A2_weight_per_hour)
			return -1;
		if (A1_weight_per_hour < A2_weight_per_hour)
			return  1;
		if (A1_weight_per_hour == A2_weight_per_hour)
			if (a1.deadline > a2.deadline)
				return 1;
			if (a1.deadline < a2.deadline)
				return -1;
			return 0;


	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	double grade = 0.0;

	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
		if (size == 0) {
			throw new Exception("There is no assignment.");
		}
		for (int i = 0; i < size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m = size;
	}


	/**
	 * @return Array where output[i] corresponds to the assignment
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {

		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());

		//TODO Implement this

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i = 0; i < lastDeadline; ++i) {
			homeworkPlan.add(-1);
			System.out.print(homeworkPlan.get(i));
		}
		System.out.println();

		double temp_grade = 0;
		int finish_time = 0;
		int current_time = 0;

		for (int i = 0; i < Assignments.size(); i++) {
			int time_consume = Assignments.get(i).completiontime;
			//int start_time = current_time;
			finish_time = current_time + Assignments.get(i).completiontime-1;


			for (int j = 0; j < time_consume; j++)
			{
				//homeworkPlan.set(current_time, Assignments.get(i).number);
				//System.out.println(homeworkPlan.get(i));
				if (current_time >= homeworkPlan.size())
				{
					homeworkPlan.add(-1);
				}
				homeworkPlan.set(current_time, Assignments.get(i).number);
				current_time++;
			}

			//calculate the grade

			int additional_time = 0;
			if (finish_time > lastDeadline)
			{
				additional_time = finish_time - lastDeadline+1;
				temp_grade = Assignments.get(i).weight * (1 - 0.1 * additional_time);
				grade = temp_grade + grade;
				return homeworkPlan;
			}
			if (finish_time >= Assignments.get(i).deadline) {
				additional_time = finish_time - Assignments.get(i).deadline + 1;
				temp_grade = Assignments.get(i).weight * (1 - 0.1 * additional_time);
			} else //(finish_time < Assignments.get(i).deadline)
			{
				temp_grade = Assignments.get(i).weight;
			}

			grade = temp_grade + grade;

		}

		return homeworkPlan;
	}


	public static void main (String[] args) throws Exception{
		//Assignment a1 = new Assignment(1, 10, 4, 3);
		//Assignment a2 = new Assignment(2, 30, 4, 3);
		int[] i1 = {30, 60,20};
		int[] i2 = {7,4,1};
		int[] i3 = {3, 3,1};
		HW_Sched hmrk = new HW_Sched(i1, i2, i3, 3);
		ArrayList<Integer> plan = hmrk.SelectAssignments();
		for (Assignment object: hmrk.Assignments)
		{
			//System.out.print(object.number);
		}
		System.out.println(Arrays.toString(plan.toArray()));
		System.out.println(hmrk.grade);
	}
}



