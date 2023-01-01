import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const Home = () => {

  const navigate = useNavigate()

  return (
    <Button icon="pi pi-home"
      className={classNames("", { "": true })}
      onClick={() => navigate("/")}
    />
  )

}

export default Home