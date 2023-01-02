import { useState } from "react"

import FoodInMeal from "./FoodInMeal"
import AddFoodForm from "./AddFoodForm"

import { Button } from "primereact/button"

const MealSummary = ({ mealType, mealDetails }) => {

  const [dialogVisible, setDialogVisible] = useState(false)

  return (
    <div className="roundedBorder flex flex-column align-self-start p-4 surface-card border-1 shadow-3" style={{ width: "100%", maxWidth: "320px", gap: "0.25rem" }}>
      <h2 className="mb-2 underline">{mealType}</h2>
      {mealDetails.map((foodDetails, index) => {
        return (
          <FoodInMeal foodDetails={foodDetails} key={index} />
        )
      })}
      <hr />
      <Button icon="pi pi-plus" label="Add Food" className="p-button-sm p-button-secondary align-self-start" onClick={() => setDialogVisible(true)} />
      <AddFoodForm dialogVisible={dialogVisible} setDialogVisible={setDialogVisible} mealType={mealType} />
    </div>
  )

}

export default MealSummary