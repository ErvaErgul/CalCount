import useZustand from "../Hooks/useZustand"

import { Outlet, Navigate } from "react-router-dom"

const Redirect = ({ redirectCondition }) => {

  const authenticationState = useZustand(state => state.authenticationState)
  const authority = useZustand(state => state.authority)

  switch (redirectCondition) {
    case "authenticationUnnecessary":
      return <>{authenticationState ? <Navigate to="/" /> : <Outlet />}</>
    case "authenticationNecessary":
      return <>{authenticationState ? <Outlet /> : <Navigate to="/authentication" />}</>
    case "adminOnly":
      return <>{authority === "admin" ? <Outlet /> : <Navigate to="/authentication" />}</>
    default:
      return <>{<Navigate to="/NotFound" />}</>
  }

}

export default Redirect