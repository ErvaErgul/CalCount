import useZustand from "../../Hooks/useZustand"

import axios from "axios"

import { useRef } from "react"
import { useState } from "react"

import { Toast } from "primereact/toast"
import { InputText } from "primereact/inputtext"
import { Password } from "primereact/password"
import { Button } from "primereact/button"

const LoginForm = () => {

  const setAppLoading = useZustand(state => state.setAppLoading)
  const date = useZustand(state => state.date)
  const setAuthenticationState = useZustand(state => state.setAuthenticationState)
  const setUserId = useZustand(state => state.setUserId)
  const setUsername = useZustand(state => state.setUsername)
  const setDailyDetails = useZustand(state => state.setDailyDetails)

  const toast = useRef(null)

  const [usernamee, setUsernamee] = useState("")
  const [password, setPassword] = useState("")

  const handleSubmit = async (e) => {
    e.preventDefault()
    setAppLoading(true)
    try {
      const response = await axios.post("/authentication/login/" + date, { username: usernamee, password: password })
      if (response.status === 200) {
        setAuthenticationState(true)
        setUserId(response.data.userId)
        setUsername(response.data.username)
        setDailyDetails(response.data.dailyDetails)
        axios.defaults.headers.common["Authorization"] = "Bearer " + response.data.jwt
      }
    } catch (error) {
      toast.current.show({ severity: "error", summary: "Login Failed", detail: "Incorrect username or password" })
    } finally {
      setAppLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-column p-3 surface-card border-round shadow-5" style={{ gap: "0.5rem" }}>
      <Toast ref={toast} />
      <h2 className="text-center">Login</h2>
      <InputText value={usernamee} onChange={(e) => setUsernamee(e.target.value)} placeholder="Username" />
      <Password value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" feedback={false} />
      <Button type="submit" label="Submit" className="p-button-sm align-self-center" />
    </form>
  )

}

export default LoginForm