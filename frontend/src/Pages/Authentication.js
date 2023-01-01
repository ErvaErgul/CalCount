import { useState } from "react"

import RegisterForm from "../Components/Authentication/RegisterForm"
import LoginForm from "../Components/Authentication/LoginForm"

import { Button } from "primereact/button"

const Authentication = () => {

  const [registerFormActive, setRegisterFormActive] = useState(false)

  return (
    <div className="justify-content-center align-items-center" id="applicationPage">
      {registerFormActive ?
        <RegisterForm setRegisterFormActive={setRegisterFormActive} />
        :
        <LoginForm setRegisterFormActive={setRegisterFormActive} />
      }
      <Button className="p-button-text p-button-link mt-3"
        onClick={() => setRegisterFormActive(!registerFormActive)}
        {...(registerFormActive ? { label: "Already have an account?" } : { label: "Don't have an account?" })}
      />
    </div>
  )

}

export default Authentication