import useZustand from "../Hooks/useZustand"

import { BlockUI } from "primereact/blockui"

const LoadingIcon = () => {

  const appLoading = useZustand(state => state.appLoading)

  return (
    <>
      {appLoading &&
        <BlockUI blocked={appLoading} fullScreen>
          <i className="pi pi-spin pi-spinner" style={{ zIndex: "999999", position: "fixed", fontSize: "3rem", left: "50%", top: "50%", color: "var(--primary-color)" }}></i>
        </BlockUI>
      }
    </>
  )

}

export default LoadingIcon