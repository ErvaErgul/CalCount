import useZustand from "../../Hooks/useZustand"

import { classNames } from "primereact/utils"

const DailyIntakeSummary = () => {

  const dailyDetails = useZustand(state => state.dailyDetails)
  const numberOfCaloriesToConsume = dailyDetails.numberOfCaloriesToConsume
  const numberOfCaloriesConsumed = dailyDetails.numberOfCaloriesConsumed
  const numberOfCaloriesLeft = dailyDetails.numberOfCaloriesLeft

  const gramsOfProteinToConsume = dailyDetails.gramsOfProteinToConsume
  const gramsOfCarbohydratesToConsume = dailyDetails.gramsOfCarbohydratesToConsume
  const gramsOfFatToConsume = dailyDetails.gramsOfFatToConsume

  const gramsOfProteinConsumed = dailyDetails.gramsOfProteinConsumed
  const gramsOfCarbohydratesConsumed = dailyDetails.gramsOfCarbohydratesConsumed
  const gramsOfFatConsumed = dailyDetails.gramsOfFatConsumed

  return (
    <div className="flex flex-column mx-auto mt-2 mb-5 p-3 surface-card border-round shadow-3 border-1 border-round" style={{ width: "100%", maxWidth: "480px" }}>
      <div className="flex justify-content-evenly">
        <div className="flex p-1 text-xl" style={{ gap: "0.25rem" }}>
          <h4 className="text-primary">Goal:</h4>
          <p>{Math.round(numberOfCaloriesToConsume)}</p>
        </div>

        <div className="flex p-1 text-xl" style={{ gap: "0.25rem" }}>
          <h4 className="text-primary">Consumed:</h4>
          <p>{Math.round(numberOfCaloriesConsumed)}</p>
        </div>

        <div className="flex p-1 text-xl" style={{ gap: "0.25rem" }}>
          <h4 className="text-primary">Left:</h4>
          <p className={classNames("", { "text-pink-300": numberOfCaloriesLeft < 0 })}>{Math.round(numberOfCaloriesLeft)}</p>
        </div>
      </div>

      <div className="flex justify-content-evenly mt-2">
        <p>{Math.round(gramsOfProteinConsumed)}/{Math.round(gramsOfProteinToConsume)} (protein)</p>
        <p>{Math.round(gramsOfCarbohydratesConsumed)}/{Math.round(gramsOfCarbohydratesToConsume)} (carbs)</p>
        <p>{Math.round(gramsOfFatConsumed)}/{Math.round(gramsOfFatToConsume)} (fat)</p>
      </div>
    </div>
  )

}

export default DailyIntakeSummary