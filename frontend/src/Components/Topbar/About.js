import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const About = () => {

  const location = useLocation()
  const navigate = useNavigate()

  return (
    <Button icon="pi pi-info-circle"
      className={classNames("p-button-rounded p-button-icon", { "bg-primary-reverse": location.pathname === "/about" })}
      onClick={() => navigate("/about")}
    />
  )

}

export default About