import useZustand from "../Hooks/useZustand"

import { Outlet } from "react-router-dom"

import { useState, useEffect } from "react"

import LoadingIcon from "../Components/LoadingIcon"

const AttemptAuthentication = () => {

  const refreshJwt = useZustand(state => state.refreshJwt)

  const [attemptingAuthentication, setAttemptingAuthentication] = useState(true)

  const checkAuthentication = async () => {
    try {
      refreshJwt()
    } catch (error) {
      console.log(error)
    } finally {
      setAttemptingAuthentication(false)
    }
  }

  useEffect(() => {
    checkAuthentication()
    //eslint-disable-next-line
  }, [])

  return (
    <>
      {attemptingAuthentication ? <LoadingIcon /> : <Outlet />}
    </>
  )

}

export default AttemptAuthentication