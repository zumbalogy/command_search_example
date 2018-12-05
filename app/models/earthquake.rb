class Earthquake
  include Mongoid::Document

  field :id, type: Integer
  field :flag_tsunami, type: Boolean
  field :date, type: Date
  field :focal_depth, type: Integer
  field :eq_primary, type: Float
  field :intensity, type: Integer
  field :country, type: String
  field :state, type: String
  field :location_name, type: String
  field :latitude, type: Float
  field :longitude, type: Float
  field :region_code, type: Integer
  field :deaths, type: Integer
  field :missing, type: Integer
  field :injuries, type: Integer
  field :damage_millions_dollars, type: Float
  field :houses_destroyed, type: Integer
  field :houses_damaged, type: Integer

end
