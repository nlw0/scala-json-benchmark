/**
 * Created by nlw on 18/04/15.
 */
case class Bird(scientific_name: String, common_names:List[String], sights: Int, wing_span:Double, hangs_out: List[Place])
case class Place(name: String, _id: Int, latlon: List[Double])

