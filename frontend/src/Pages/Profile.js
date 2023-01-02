import useZustand from "../Hooks/useZustand"

import axios from "axios"

import { useRef } from "react"
import { useState } from "react"
import { useEffect } from "react"

import { classNames } from "primereact/utils"
import { Toast } from "primereact/toast"
import { InputNumber } from "primereact/inputnumber"
import { Dropdown } from "primereact/dropdown"
import { Button } from "primereact/button"

const ageOptions = Array.from({ length: 77 }, (_, i) => i + 14)

const activityLevelOptions = [
  { label: "Very Active", value: 5 },
  { label: "Active", value: 4 },
  { label: "Mixed", value: 3 },
  { label: "Sedentary", value: 2 },
  { label: "Very Sedentary", value: 1 },
]

const goalOptions = [
  { label: "Lose Weight", value: "loseWeight" },
  { label: "Maintain Weight", value: "maintainWeight" },
  { label: "Gain Weight", value: "gainWeight" },
]

const Profile = () => {

  const setAppLoading = useZustand(state => state.setAppLoading)
  const getDailyDetails = useZustand(state => state.getDailyDetails)
  const userId = useZustand(state => state.userId)

  const toast = useRef(null)

  const [userDetails, setUserDetails] = useState("")
  const [age, setAge] = useState("")
  const [height, setHeight] = useState()
  const [weight, setWeight] = useState()
  const [activityLevel, setActivityLevel] = useState("")
  const [goal, setGoal] = useState("")

  const [dailyCalorieGoal, setDailyCalorieGoal] = useState("")
  const [dailyProteinGoal, setDailyProteinGoal] = useState("")
  const [dailyCarbohydratesGoal, setDailyCarbohydratesGoal] = useState("")
  const [dailyFatGoal, setDailyFatGoal] = useState("")

  const [formDisabled, setFormDisabled] = useState(true)

  const getUserDetails = async () => {
    setAppLoading(true)
    try {
      const response = await axios.get("/users/" + userId)
      if (response.status === 200) {
        setUserDetails(response.data)
        setAge(response.data.age)
        setHeight(response.data.height)
        setWeight(response.data.weight)
        setActivityLevel(response.data.activityLevel)
        setGoal(response.data.goal)
        setDailyCalorieGoal(Math.round(response.data.dailyCalorieGoal))
        setDailyProteinGoal(Math.round(response.data.dailyProteinGoal))
        setDailyCarbohydratesGoal(Math.round(response.data.dailyCarbohydrateGoal))
        setDailyFatGoal(Math.round(response.data.dailyFatGoal))
      }
    } catch (error) {
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  const updateUserCredentials = async () => {
    setAppLoading(true)
    try {
      const response = await axios.put("/users/no", { userId: userDetails.userId, age: age, height: height, weight: weight, activityLevel: activityLevel, goal: goal, dailyCalorieGoal: dailyCalorieGoal, dailyProteinGoal: dailyProteinGoal, dailyCarbohydrateGoal: dailyCarbohydratesGoal, dailyFatGoal: dailyFatGoal })
      if (response.status === 200) {
        toast.current.show({ severity: "success", detail: "Update Successful" })
        getDailyDetails()
        setFormDisabled(true)
      }
    } catch (error) {
      toast.current.show({ severity: "error", detail: "Update Unsuccessful" })
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  const calculateDailyGoalsForUser = async () => {
    setAppLoading(true)
    try {
      const response = await axios.get("/users/" + userId + "/calculateDailyGoal")
      if (response.status === 200) {
        toast.current.show({ severity: "success", summary: "Recalculated Daily Goals" })
        getUserDetails()
        getDailyDetails()
      }
    } catch (error) {
      toast.current.show({ severity: "error", summary: "Recalculation Failed" })
      console.log(error)
    } finally {
      setAppLoading(false)
    }
  }

  useEffect(() => {
    getUserDetails()
    //eslint-disable-next-line
  }, [])

  return (
    <div id="applicationPage">
      <Toast ref={toast} />

      <div className={classNames("flex flex-column align-items-center mx-auto p-3 surface-card border-1 border-primary shadow-3", { "opacity-80": formDisabled })} style={{ width: "100%", maxWidth: "480px", gap: "0.5rem" }}>

        <div className="flex align-self-stretch">
          <Button label="Calculate Daily Goals For Me"
            className={classNames("flex-1 mr-8", { "p-disabled": !formDisabled })}
            onClick={() => calculateDailyGoalsForUser()}
          />

          <Button icon="pi pi-pencil"
            className={classNames("ml-auto", { "bg-primary-reverse": !formDisabled })}
            onClick={() => setFormDisabled(!formDisabled)}
          />
        </div>

        <h4>Age</h4>
        <Dropdown required value={age} options={ageOptions} onChange={(e) => setAge(e.value)} placeholder="Age" disabled={formDisabled} />
        <h4>Height</h4>
        <InputNumber required value={height} onChange={(e) => setHeight(e.value)} placeholder="Height (cm)" keyfilter="int" suffix=" cm" disabled={formDisabled} />
        <h4>Weight</h4>
        <InputNumber required value={weight} onChange={(e) => setWeight(e.value)} placeholder="Weight (kg)" keyfilter="int" suffix=" kg" disabled={formDisabled} />
        <h4>Activity Level</h4>
        <Dropdown required value={activityLevel} options={activityLevelOptions} onChange={(e) => setActivityLevel(e.value)} placeholder="Activity Level" optionLabel="label" optionValue="value" disabled={formDisabled} />
        <h4>Goal</h4>
        <Dropdown required value={goal} options={goalOptions} onChange={(e) => setGoal(e.value)} placeholder="Goal" optionLabel="label" optionValue="value" disabled={formDisabled} />
        <h4>Daily Calorie Goal</h4>
        <InputNumber required value={dailyCalorieGoal} onChange={(e) => setDailyCalorieGoal(e.value)} keyfilter="int" disabled={formDisabled} />
        <h4>Daily Protein Goal</h4>
        <InputNumber required value={dailyProteinGoal} onChange={(e) => setDailyProteinGoal(e.value)} keyfilter="int" suffix=" grams" disabled={formDisabled} />
        <h4>Daily Carbohydrate Goal</h4>
        <InputNumber required value={dailyCarbohydratesGoal} onChange={(e) => setDailyCarbohydratesGoal(e.value)} keyfilter="int" suffix=" grams" disabled={formDisabled} />
        <h4>Daily Fat Goal</h4>
        <InputNumber required value={dailyFatGoal} onChange={(e) => setDailyFatGoal(e.value)} keyfilter="int" suffix=" grams" disabled={formDisabled} />
        <Button label="Update" disabled={formDisabled}
          onClick={() => updateUserCredentials()}
        />
      </div>
    </div>
  )

}

export default Profile