import useZustand from "../../Hooks/useZustand"

import axios from "axios"

import { useState } from "react"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"
import { Dialog } from "primereact/dialog"
import { InputNumber } from "primereact/inputnumber"

const FoodInMeal = ({ foodDetails }) => {

  const setAppLoading = useZustand(state => state.setAppLoading)
  const getDailyDetails = useZustand(state => state.getDailyDetails)

  const [dialogVisible, setDialogVisible] = useState(false)
  const [initialAmountOfGrams] = useState(foodDetails.amountOfGrams)
  const [amountOfGrams, setAmountOfGrams] = useState(foodDetails.amountOfGrams)
  const [numberOfCalories, setNumberOfCalories] = useState(foodDetails.numberOfCalories)
  const [gramsOfProtein, setGramsOfProtein] = useState(foodDetails.gramsOfProtein)
  const [gramsOfCarbohydrates, setGramsOfCarbohydrates] = useState(foodDetails.gramsOfCarbohydrates)
  const [gramsOfFat, setGramsOfFat] = useState(foodDetails.gramsOfFat)

  const handleAmountOfGramsUpdate = (newAmount) => {
    setAmountOfGrams(newAmount)
    setNumberOfCalories(foodDetails.caloriePerGram * newAmount)
    setGramsOfProtein(foodDetails.proteinPerGram * newAmount)
    setGramsOfCarbohydrates(foodDetails.carbohydratesPerGram * newAmount)
    setGramsOfFat(foodDetails.fatPerGram * newAmount)
  }

  const updateFoodPointer = async () => {
    setAppLoading(true)
    try {
      const respose = await axios.put("/foodpointers/" + foodDetails.foodPointerId + "/" + amountOfGrams)
      if (respose.status === 200) {
        getDailyDetails()
      }
    } catch (error) {
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  const handleFoodInMealRemoval = async () => {
    setAppLoading(true)
    try {
      const respose = await axios.delete("/foodpointers/" + foodDetails.foodPointerId)
      if (respose.status === 200) {
        getDailyDetails()
      }
    } catch (error) {
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  const handleDialogClose = async () => {
    setDialogVisible(false)
    if (initialAmountOfGrams !== amountOfGrams) {
      updateFoodPointer()
    }
  }

  return (
    <div className="flex" style={{ gap: "0.25rem" }}>
      <Button label={foodDetails.name + " (" + foodDetails.amountOfGrams + " grams)"}
        className={classNames("p-button-sm flex-1 p-1 font-semibold", { "": true })}
        onClick={() => setDialogVisible(true)}
      />

      <Button icon="pi pi-minus"
        className={classNames("p-button-text", { "": true })}
        onClick={() => handleFoodInMealRemoval()}
      />

      <Dialog visible={dialogVisible} onHide={() => handleDialogClose()} header={foodDetails.name} dismissableMask draggable={false}>
        <div className="flex flex-column" style={{ gap: "0.5rem" }}>
          <div className="flex">
            <InputNumber value={amountOfGrams} onChange={(e) => handleAmountOfGramsUpdate(e.value)} suffix=" grams" />
          </div>

          <div className="flex">
            <h4 className="text-primary">Calories: </h4><p className="ml-1"> {numberOfCalories.toFixed(2)}</p>
          </div>

          <div className="flex">
            <h4 className="text-primary">Protein: </h4><p className="ml-1"> {gramsOfProtein.toFixed(2)}</p>
          </div>

          <div className="flex">
            <h4 className="text-primary">Carbohydrates: </h4><p className="ml-1"> {gramsOfCarbohydrates.toFixed(2)}</p>
          </div>

          <div className="flex">
            <h4 className="text-primary">Fats: </h4><p className="ml-1"> {gramsOfFat.toFixed(2)}</p>
          </div>
        </div>
      </Dialog>
    </div>
  )

}

export default FoodInMeal