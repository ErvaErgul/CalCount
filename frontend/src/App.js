import useZustand from "./Hooks/useZustand"

import { BrowserRouter as Router, Routes, Route } from "react-router-dom"

import { useEffect } from "react"

import ApplicationLayout from "./Routes/ApplicationLayout"
import Redirect from "./Routes/Redirect"

import Authentication from "./Pages/Authentication"
import Home from "./Pages/Home"
import CreateFood from "./Pages/CreateFood"
import Profile from "./Pages/Profile"
import Admin from "./Pages/Admin"
import About from "./Pages/About"
import NotFound from "./Pages/NotFound"
import AttemptAuthentication from "./Routes/AttemptAuthentication"

const App = () => {

  const shouldAttemptAuthentication = useZustand(state => state.shouldAttemptAuthentication)
  const refreshJwt = useZustand(state => state.refreshJwt)

  useEffect(() => {
    if (shouldAttemptAuthentication) {
      const refreshJwtPeriodically = setInterval(() => {
        refreshJwt()
      }, 3590000)
      return () => clearInterval(refreshJwtPeriodically)
    }
    // eslint-disable-next-line
  }, [])

  return (
    <Router>
      <Routes>
        <Route element={<AttemptAuthentication />}>
          <Route element={<ApplicationLayout />}>


            <Route element={<Redirect redirectCondition={"authenticationUnnecessary"} />}>
              <Route path="/authentication" element={<Authentication />} />
            </Route>

            <Route element={<Redirect redirectCondition={"authenticationNecessary"} />}>
              <Route path="/" element={<Home />}></Route>
              <Route path="/createFood" element={<CreateFood />} />
              <Route path="/profile" element={<Profile />} />
            </Route>

            <Route element={<Redirect redirectCondition={"adminOnly"} />}>
              <Route path="/admin" element={<Admin />} />
            </Route>

            <Route path="/about" element={<About />} />

            <Route path="*" element={<NotFound />} />

          </Route>
        </Route>
      </Routes>
    </Router >
  )

}

export default App
