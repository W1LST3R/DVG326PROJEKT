package activity;

public class ActtivityTest {
	public static void main(String[] args) {
	//Activity atest = new Activity("activity_4105080982.csv");
	Activity atest = new Activity("activity_2001397372.csv");
	ActivityCalculator activityCalculator = new ActivityCalculator(atest.getActivityData());
	//System.out.println(activityCalculator.verticalUp());
	//System.out.println(activityCalculator.verticalDown());
	//System.out.println(activityCalculator.meanSpeedVerticalUp());
	//System.out.println(activityCalculator.meanSpeedVerticalDown());
	//System.out.println(activityCalculator.highestInclination());
	//System.out.println(activityCalculator.lowestInclination());
	//System.out.println(activityCalculator.bestKm());
	//System.out.println(activityCalculator.worstKm());
	//System.out.println(activityCalculator.uniqueValues("HeartRate"));
	//System.out.println(activityCalculator.timeInZone("HeartRate"));
	//System.out.println(activityCalculator.perKm("HeartRate"));
	System.out.println(activityCalculator.maxValue(activityCalculator.perKm("Speed")));
	}
}
