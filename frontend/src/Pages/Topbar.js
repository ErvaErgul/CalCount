import QueryField from "../Components/Topbar/QueryField"
import Avatar from "../Components/Topbar/Avatar"
import About from "../Components/Topbar/About"
import Home from "../Components/Topbar/Home"

const Topbar = () => {

  return (
    <div className="shadow-3" id="applicationTopbar" style={{ gap: "0.25rem" }}>
      <Home />
      <QueryField />
      <h1 className="mx-auto align-self-center font-italic font-medium">Trackcals</h1>
      <About />
      <Avatar />
    </div>

  )

}

export default Topbar