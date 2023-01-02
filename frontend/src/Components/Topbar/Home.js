import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const Home = () => {

  const location = useLocation()
  const navigate = useNavigate()

  return (
    <Button icon="pi pi-home"
      className={classNames("", { "bg-primary-reverse": location.pathname === "/" })}
      onClick={() => navigate("/")}
    />
  )

}

export default Home