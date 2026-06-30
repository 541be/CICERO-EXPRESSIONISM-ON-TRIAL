(ns cicero.db)

(def levels
  {1 "Sie wollten den wilden Tieren seinen Leib nicht vorwerfen, damit nicht die Tiere durch Berührung eines solchen Gräuels noch grausamer gegen uns werden möchten; man wollte sie nicht geradezu nackt in den Fluss werfen, damit nicht, wenn sie ins Meer getrieben würden, eben das entweiht werde, von dem man glaubt, dass es alle anderen befleckten Dinge reinige."
   2 "Denn was ist so allgemein, als der Atem den Lebenden, die Erde den Toten, das Meer den Dahintreibenden, das Gestade den Gestrandeten. Also leben sie, solange es noch möglich ist, so, dass sie die Himmelsluft nicht einatmen können, sie sterben so, dass die Erde ihre Gebeine nicht berührt; sie werden von den Wogen so dahingetrieben, dass sie nie von ihnen abgewaschen werden, sie stranden endlich so, dass sie im Tod nicht einmal an Felsen Ruhe finden."
   3 "Du besitzt meine Landgüter, ich lebe von fremden Mitleid; ich lasse mir das gefallen, sowohl aus Gleichmut als auch, weil es so sein muss. Mein Haus steht dir offen; mir ist es verschlossen: ich ertrage es. Dir steht meine so zahlreiche Dienerschaft zu Gebot; ich habe keine Sklaven: ich dulde es und glaube, es dulden zu müssen."
   4 "ZORN DES CICERO: Denn glaubt nicht, dass diejenigen, die etwas Ruchloses und Frevelhaftes verübt haben, so wie ihr es auf der Bühne oft seht, durch brennende Fackeln der Furien umhergescheucht und geängstigt werden. Die eigene Schuld, die innewohnende Angst, das eigene Verbrechen ist es eigentlich, was jeden quält, beunruhigt und zum Wahnsinn treibt; des eigenen Herzens schlimme Gedanken und das böse Gewissen ist es, was ihn erschreckt!"})

(def default-db
  {:scene :main-menu ; :main-menu, :trial, :encyclopedia, :settings
   :level 1
   :score 0
   :k-marks 0
   :cicero-text (get levels 1)
   :player-text ""
   :ai-evaluating? false
   :ai-result nil ; {:score 0-100, :explanation "..."}
   :api-backend :ollama ; :ollama or :gemini
   :gemini-api-key ""
   })
