import { useState, useEffect } from "react"

const mQuery0 = matchMedia("only screen and (min-width:1025px)")
const mQuery1 = matchMedia("only screen and (max-width:1024px) and (min-width:481px)")
const mQuery2 = matchMedia("only screen and (max-width:480px)")

const checkCurrentScreen = () => {
  if (mQuery0.matches) return "big"
  if (mQuery1.matches) return "medium"
  if (mQuery2.matches) return "small"
}

const useScreenSize = () => {

  const [activeScreenSize, setActiveScreenSize] = useState(checkCurrentScreen())

  useEffect(() => {
    const handleResize = () => {
      const currentScreenSize = checkCurrentScreen()
      if (currentScreenSize !== activeScreenSize) {
        setActiveScreenSize(currentScreenSize)
      }
    }

    window.addEventListener("resize", handleResize)

    return () => window.removeEventListener("resize", handleResize)
  }, [activeScreenSize])

  return activeScreenSize

}

export default useScreenSize