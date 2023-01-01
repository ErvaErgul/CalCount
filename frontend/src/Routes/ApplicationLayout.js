import { Outlet } from "react-router-dom"

import LoadingIcon from "../Components/LoadingIcon"
import Topbar from "../Pages/Topbar"

const ApplicationLayout = () => {

  return (
    <>
      <LoadingIcon />
      <Topbar />
      <Outlet />
    </>
  )

}

export default ApplicationLayout