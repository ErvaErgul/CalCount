import FoodInMeal from "./FoodInMeal"

const MealSummary = ({ mealType, mealDetails }) => {

  return (
    <div className="flex flex-column align-self-start p-4 surface-card border-round border-1 shadow-3" style={{ width: "100%", maxWidth: "320px", gap: "0.25rem" }}>
      <h2 className="mb-2">{mealType}</h2>
      {mealDetails.map((foodDetails, index) => {
        return (
          <FoodInMeal foodDetails={foodDetails} key={index} />
        )
      })}
    </div>
  )

}

export default MealSummary