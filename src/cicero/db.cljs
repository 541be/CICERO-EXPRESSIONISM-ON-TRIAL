(ns cicero.db)

(def levels
  {1 "Credo ego vos, iudices, mirari, quid sit, quod, cum tot summi oratores hominesque nobilissimi sedeant, ego potissimum surrexerim..."
   2 "Si vobis aequa et honesta postulatio videtur, iudices, ego contra brevem postulationem adfero et, quo modo mihi persuadeo, aliquanto aequiorem."
   3 "His de rebus tantis tamque atrocibus neque satis me commode dicere neque satis graviter conqueri neque satis libere vociferari posse intellego. Nam commoditati ingenium, gravitati aetas, libertati tempora sunt impedimento."
   4 "THE FURY OF CICERO: Quod si aut causa criminis aut facti suspicio aut quaelibet denique vel minima res reperietur, quam ob rem videantur illi non nihil tamen in deferendo nomine secuti... non recusamus, quin illorum libidini Sex. Rosci vita dedatur!"})

(def default-db
  {:scene :main-menu ; :main-menu, :trial, :encyclopedia
   :level 1
   :score 0
   :k-marks 0
   :cicero-text (get levels 1)
   :player-text ""
   :ai-evaluating? false
   :ai-result nil ; {:score 0-100, :explanation "..."}
   })
