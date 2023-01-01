import useZustand from "../../Hooks/useZustand"

import axios from "axios"

import { useState } from "react"
import { useRef } from "react"

import { Toast } from "primereact/toast"
import { InputText } from "primereact/inputtext"
import { InputNumber } from "primereact/inputnumber"
import { Password } from "primereact/password"
import { Dropdown } from "primereact/dropdown"
import { Button } from "primereact/button"

const ageOptions = Array.from({ length: 77 }, (_, i) => i + 14)

const genderOptions = [
  { label: "Male", value: 0 },
  { label: "Female", value: 1 }
]

const activityLevelOptions = [
  { label: "Very Active", value: "5" },
  { label: "Active", value: "5" },
  { label: "Mixed", value: "3" },
  { label: "Sedentary", value: "2" },
  { label: "Very Sedentary", value: "1" },
]

const goalOptions = [
  { label: "Lose Weight", value: "loseWeight" },
  { label: "Maintain Weight", value: "maintainWeight" },
  { label: "Gain Weight", value: "gainWeight" },
]

const RegisterForm = () => {

  const setAppLoading = useZustand(state => state.setAppLoading)
  const setAuthenticationState = useZustand(state => state.setAuthenticationState)
  const setUserId = useZustand(state => state.setUserId)
  const setUsername = useZustand(state => state.setUsername)
  const setDailyDetails = useZustand(state => state.setDailyDetails)

  const toast = useRef(null)

  const [usernamee, setUsernamee] = useState("")
  const [password, setPassword] = useState("")
  const [age, setAge] = useState("")
  const [gender, setGender] = useState("")
  const [height, setHeight] = useState()
  const [weight, setWeight] = useState()
  const [activityLevel, setActivityLevel] = useState("")
  const [goal, setGoal] = useState("")

  const handleSubmit = async (e) => {
    e.preventDefault()
    setAppLoading(true)
    try {
      const response = await axios.post("/users", { username: usernamee, password: password, age: age, gender: String(gender), height: height, weight: weight, activityLevel: Number(activityLevel), goal: goal })
      if (response.status === 201) {
        const response = await axios.post("/authentication/login/" + ((new Date().toLocaleString() + "").split(",")[0]).replaceAll("/", "-"), { username: usernamee, password: password })
        if (response.status === 200) {
          localStorage.setItem("shouldAttemptAuthentication", true)
          setAuthenticationState(true)
          setUserId(response.data.userId)
          setUsername(response.data.username)
          setDailyDetails(response.data.dailyDetails)
          axios.defaults.headers.common["Authorization"] = "Bearer " + response.data.jwt
        }
      }
    } catch (error) {
      toast.current.show({ severity: "error", summary: "Registration Failed", detail: "Username is already taken." })
    } finally {
      setAppLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-column  p-3 surface-card border-round shadow-5" style={{ gap: "0.5rem" }}>
      <Toast ref={toast} />
      <h2 className="text-center">Register</h2>
      <InputText required value={usernamee} onChange={(e) => setUsernamee(e.target.value)} placeholder="Username" />
      <Password required value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" feedback={false} />
      <Dropdown required value={age} options={ageOptions} onChange={(e) => setAge(e.value)} placeholder="Age" />
      <Dropdown required value={gender} options={genderOptions} onChange={(e) => setGender(e.value)} placeholder="Gender" optionLabel="label" optionValue="value" />
      <InputNumber required value={height} onChange={(e) => setHeight(e.value)} placeholder="Height (cm)" keyfilter="int" suffix=" cm" />
      <InputNumber required value={weight} onChange={(e) => setWeight(e.value)} placeholder="Weight (kg)" keyfilter="int" suffix=" kg" />
      <Dropdown required value={activityLevel} options={activityLevelOptions} onChange={(e) => setActivityLevel(e.value)} placeholder="Activity Level" optionLabel="label" optionValue="value" />
      <Dropdown required value={goal} options={goalOptions} onChange={(e) => setGoal(e.value)} placeholder="Goal" optionLabel="label" optionValue="value" />
      <Button type="submit" label="Submit" className="p-button-sm align-self-center" />
    </form>
  )

}

export default RegisterForm