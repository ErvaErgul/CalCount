import create from "zustand"
import axios from "axios"

const useZustand = create((set, get) => ({

  appLanguage: localStorage.getItem("appLanguage") === null ? "english" : localStorage.getItem("appLanguage"),
  setAppLanguage: (prefLanguage) => {
    localStorage.setItem("appLanguage", prefLanguage)
    set({ appLanguage: prefLanguage })
  },

  date: (new Date().toLocaleString() + "").split(",")[0].replaceAll("/", "-"),

  appLoading: false,
  setAppLoading: (loadingState) => { set({ appLoading: loadingState }) },

  authenticationState: null,
  setAuthenticationState: (authenticationState) => { set({ authenticationState: authenticationState }) },

  userId: null,
  setUserId: (userId) => { set({ userId: userId }) },

  username: null,
  setUsername: (username) => { set({ username: username }) },

  authority: null,
  setAuthority: (authority) => { set({ authority: authority }) },

  dailyDetails: null,
  setDailyDetails: (dailyDetails) => { set({ dailyDetails: dailyDetails }) },
  getDailyDetails: async () => {
    const userId = get().userId
    const date = get().date
    set({ appLoading: true })
    try {
      const response = await axios.get("/dailies/" + userId + "/" + date)
      if (response.status === 200) {
        set({ dailyDetails: response.data })
      }
    } catch (error) {
      console.log(error)
    } finally {
      set({ appLoading: false })
    }
  },

  refreshJwt: async () => {
    const date = get().date
    set({ appLoading: true })
    try {
      const response = await axios.put("/authentication/refreshJwt/" + date)
      if (response.status === 200) {
        set({ authenticationState: true })
        set({ userId: response.data.userId })
        set({ username: response.data.username })
        set({ dailyDetails: response.data.dailyDetails })
        axios.defaults.headers.common["Authorization"] = "Bearer " + response.data.jwt
      }
    } catch (error) {
      console.log(error)
    } finally {
      set({ appLoading: false })
    }
  }

}))

export default useZustand