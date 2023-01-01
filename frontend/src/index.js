import React from "react"
import ReactDOM from "react-dom/client"

import App from "./App"

import axios from "axios"

import PrimeReact from "primereact/api"
import "primereact/resources/primereact.min.css"
import "primeicons/primeicons.css"
import "primeflex/primeflex.css"

import "./Assets/index.css"
import "./Assets/theme.css"
import "./Assets/themeChanges.css"
import "./Assets/utility.css"
import "./Assets/mQuery0.css"
import "./Assets/mQuery1.css"
import "./Assets/mQuery2.css"


axios.defaults.baseURL = "http://localhost:8080/api"
axios.defaults.withCredentials = true

PrimeReact.ripple = true

const root = ReactDOM.createRoot(document.getElementById("root"))

root.render(<App />)