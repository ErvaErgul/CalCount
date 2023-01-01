import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const About = () => {

  const navigate = useNavigate()

  return (
    <Button icon="pi pi-info-circle"
      className={classNames("p-button-rounded ml-auto", { "": true })}
      onClick={() => navigate("/about")}
    />
  )

}

export default About