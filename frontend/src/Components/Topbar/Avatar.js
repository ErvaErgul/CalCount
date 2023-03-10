import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const Avatar = () => {

  const location = useLocation()
  const navigate = useNavigate()

  return (
    <Button icon="pi pi-user"
      className={classNames("p-button-rounded", { "bg-primary-reverse": location.pathname === "/profile" || location.pathname === "/authentication" })}
      onClick={() => navigate("/profile")}
    />
  )

}

export default Avatar