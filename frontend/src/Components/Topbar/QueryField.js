import useZustand from "../../Hooks/useZustand"

import axios from "axios"

import { useState } from "react"
import { useEffect } from "react"

import { classNames } from "primereact/utils"
import { AutoComplete } from "primereact/autocomplete"
import { Dialog } from "primereact/dialog"
import { Dropdown } from "primereact/dropdown"
import { InputNumber } from "primereact/inputnumber"
import { Button } from "primereact/button"

const mealOptions = [
  { label: "Breakfast", value: "breakfast" },
  { label: "Lunch", value: "lunch" },
  { label: "Dinner", value: "dinner" },
  { label: "Snack", value: "snack" },
]

const QueryField = () => {

  const setAppLoading = useZustand(state => state.setAppLoading)
  const date = useZustand(state => state.date)
  const userId = useZustand(state => state.userId)
  const getDailyDetails = useZustand(state => state.getDailyDetails)

  const [foodSuggestions, setFoodSuggestions] = useState([])
  const [selectedFood, setSelectedFood] = useState("")
  const [dialogVisible, setDialogVisible] = useState(false)

  const [selectedMeal, setSelectedMeal] = useState("")
  const [amountOfGrams, setAmountOfGrams] = useState(100)

  const [numberOfCalories, setNumberOfCalories] = useState(0)
  const [gramsOfProtein, setGramsOfProtein] = useState(0)
  const [gramsOfCarbohydrates, setGramsOfCarbohydrates] = useState(0)
  const [gramsOfFat, setGramsOfFat] = useState(0)

  const handleQueryParameterChange = async (e) => {
    setSelectedFood(e.query)
    setTimeout(async () => {
      try {
        const response = await axios.get("/foods/query/" + e.query)
        if (response.status === 200) {
          setFoodSuggestions(response.data)
          return response.data.name
        }
      } catch (error) {
        setFoodSuggestions([])
      }
    }, 250)
  }

  const handleSelection = (e) => {
    setSelectedFood(e.value)
    setDialogVisible(true)
  }

  const handleAmountOfGramsChange = (e) => {
    setAmountOfGrams(e)
    setNumberOfCalories(selectedFood.caloriesPerGram * e)
    setGramsOfProtein(selectedFood.proteinPerGram * e)
    setGramsOfCarbohydrates(selectedFood.carbohydratesPerGram * e)
    setGramsOfFat(selectedFood.fatPerGram * e)
  }

  const addFoodToMeal = async () => {
    if (selectedMeal !== "") {
      setAppLoading(true)
      try {
        const response = await axios.post("/foodpointers/" + userId + "/" + date + "/" + selectedFood.foodId + "/" + Number(amountOfGrams) + "/" + selectedMeal)
        if (response.status === 200) {
          setDialogVisible(false)
          setSelectedFood("")
          getDailyDetails()
        }
      } catch (error) {
        console.log(error)
      } finally {
        setAppLoading(false)
      }
    }
  }

  useEffect(() => {
    setNumberOfCalories(selectedFood.caloriesPerGram * amountOfGrams)
    setGramsOfProtein(selectedFood.proteinPerGram * amountOfGrams)
    setGramsOfCarbohydrates(selectedFood.carbohydratesPerGram * amountOfGrams)
    setGramsOfFat(selectedFood.fatPerGram * amountOfGrams)
    //eslint-disable-next-line
  }, [selectedFood])

  const itemTemplate = (item) => {
    console.log(item)
    return (
      <div className={classNames("flex flex-column gap-2", { "": item.verified })}>
        <div className="flex" style={{ gap: "0.25rem" }}>
          {item.name}
          {item.verified && <i alt="verifiedIcon" className="pi pi-verified text-blue-300" />}
        </div>
        <p className="text-sm">({item.caloriesPer100Grams} calories per 100 grams)</p>
      </div>
    )
  }

  return (
    <>
      <AutoComplete value={selectedFood} suggestions={foodSuggestions} onChange={(e) => setSelectedFood(e.value)} onSelect={(e) => handleSelection(e)} completeMethod={handleQueryParameterChange} field="name" placeholder="Search" itemTemplate={itemTemplate}
        className={classNames("", { "text-blue-300": selectedFood.verified })}
      />

      <Dialog onHide={() => setDialogVisible(false) & setSelectedFood("")} visible={dialogVisible} dismissableMask draggable={false} header="Add Food">
        <div className="flex flex-column" style={{ gap: "0.2rem" }}>
          <Dropdown value={selectedMeal} options={mealOptions} onChange={(e) => setSelectedMeal(e.value)} optionLabel="label" optionValue="value" placeholder="Meal" />
          <InputNumber value={amountOfGrams} onChange={(e) => handleAmountOfGramsChange(e.value)} suffix=" grams" placeholder="grams" />

          <div className="flex flex-column" style={{ gap: "0.5rem" }}>
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

          <Button label="Add"
            onClick={() => addFoodToMeal()}
          />
        </div>
      </Dialog>
    </>
  )

}

export default QueryField