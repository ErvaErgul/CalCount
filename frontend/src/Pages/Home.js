import useZustand from "../Hooks/useZustand"

import DailyIntakeSummary from "../Components/Global/DailyIntakeSummary"
import MealSummary from "../Components/Home/MealSummary"

const Home = () => {

  const authenticationState = useZustand(state => state.authenticationState)
  const dailyDetails = useZustand(state => state.dailyDetails)

  return (
    <div id="applicationPage">
      {authenticationState && dailyDetails !== null && dailyDetails !== undefined &&
        <DailyIntakeSummary />
      }
      {dailyDetails !== null && dailyDetails !== undefined &&
        <div className="flex flex-1 flex-wrap justify-content-around mt-3" style={{ gap: "0.5rem" }}>
          <MealSummary mealType="Breakfast" mealDetails={dailyDetails.breakfastFoods} />
          <MealSummary mealType="Lunch" mealDetails={dailyDetails.lunchFoods} />
          <MealSummary mealType="Dinner" mealDetails={dailyDetails.dinnerFoods} />
          <MealSummary mealType="Snack" mealDetails={dailyDetails.snackFoods} />
        </div>
      }
    </div>
  )

}

export default Home