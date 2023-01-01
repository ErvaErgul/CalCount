import { useNavigate } from "react-router-dom"

import { classNames } from "primereact/utils"
import { Button } from "primereact/button"

const Avatar = () => {

  const navigate = useNavigate()

  return (
    <Button icon="pi pi-user"
      className={classNames("p-button-rounded", { "": true })}
      onClick={() => navigate("/profile")}
    />
  )

}

export default Avatar