import useZustand from "../Hooks/useZustand"

import axios from "axios"

import { useRef } from "react"
import { useState } from "react"

import { classNames } from "primereact/utils"
import { Toast } from "primereact/toast"
import { InputText } from "primereact/inputtext"
import { InputNumber } from "primereact/inputnumber"
import { Dropdown } from "primereact/dropdown"
import { Button } from "primereact/button"

const languageOptions = [
  { label: "Turkish", value: "turkish" }
]

const CreateFood = () => {

  const setAppLoading = useZustand(state => state.setAppLoading)

  const toast = useRef(null)

  const [englishName, setEnglishName] = useState("")
  const [englishDescription, setEnglishDescription] = useState("")

  const [caloriesPer100Grams, setCaloriesPer100Grams] = useState()
  const [proteinPer100Grams, setProteinPer100Grams] = useState()
  const [carbohydratesPer100Grams, setCarbohydratesPer100Grams] = useState()
  const [fatPer100Grams, setFatPer100Grams] = useState()

  const [additionalLanguage, setAdditionalLanguage] = useState("")
  const [additionalLanguageNameTranslation, setAdditionalLanguageNameTranslation] = useState("")
  const [additionalLanguageDescriptionTranslation, setAdditionalLanguageDescriptionTranslation] = useState("")

  const createFood = async (e) => {
    e.preventDefault()
    setAppLoading(true)
    try {
      const response = await axios.post("/foods", { englishName: englishName, englishDescription: englishDescription, caloriesPer100Grams: caloriesPer100Grams, proteinPer100Grams: proteinPer100Grams, carbohydratesPer100Grams: carbohydratesPer100Grams, fatPer100Grams: fatPer100Grams, additionalLanguage: additionalLanguage, additionalLanguageNameTranslation, additionalLanguageDescriptionTranslation })
      if (response.status === 200) {
        toast.current.show({ severity: "success", summary: "Food Creation Successful" })
      }
    } catch (error) {
      toast.current.show({ severity: "error", summary: "Food Creation Unsuccessful" })
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  return (
    <div id="applicationPage">
      <Toast ref={toast} />
      <form onSubmit={createFood} className="flex flex-column align-self-center p-3 surface-card shadow-3 roundedBorder" style={{ gap: "0.5rem" }}>
        <h1 className="text-center underline">Create Food</h1>
        <InputText required value={englishName} onChange={(e) => setEnglishName(e.target.value)} placeholder="English Name" />
        <InputText value={englishDescription} onChange={(e) => setEnglishDescription(e.target.value)} placeholder="English Description" />
        <h2 className="text-center underline">Per 100 Grams</h2>
        <InputNumber required value={caloriesPer100Grams} onChange={(e) => setCaloriesPer100Grams(e.value)} placeholder="calories" suffix=" calories" />
        <InputNumber required value={proteinPer100Grams} onChange={(e) => setProteinPer100Grams(e.value)} placeholder="protein" suffix=" grams of protein" />
        <InputNumber required value={carbohydratesPer100Grams} onChange={(e) => setCarbohydratesPer100Grams(e.value)} placeholder="carbohydrates" suffix=" grams of carbohydrates" />
        <InputNumber required value={fatPer100Grams} onChange={(e) => setFatPer100Grams(e.value)} placeholder="fat" suffix=" grams of fat" />
        <Dropdown options={languageOptions} value={additionalLanguage} onChange={(e) => setAdditionalLanguage(e.value)} placeholder="Extra Language" />
        {(additionalLanguage !== null && additionalLanguage !== undefined && additionalLanguage !== "") &&
          <>
            <InputText value={additionalLanguageNameTranslation} onChange={(e) => setAdditionalLanguageNameTranslation(e.target.value)} placeholder={additionalLanguage + " Name"}
              {...((additionalLanguage !== null || additionalLanguage !== undefined || additionalLanguage !== "") && { required: true })}
            />
            <InputText value={additionalLanguageDescriptionTranslation} onChange={(e) => setAdditionalLanguageDescriptionTranslation(e.target.value)} placeholder={additionalLanguage + " Description"} />
          </>
        }
        <Button label="Create" type="submit"
          className={classNames("align-self-center", { "": true })}
          onClick={() => createFood()}
        />
      </form>
    </div>
  )

}

export default CreateFood