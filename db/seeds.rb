require('csv')
require('mongoid')

data_file = File.read(__dir__ + '/../earthquakes.csv')

quake_data = CSV.new(data_file, headers: true).map(&:to_hash)

Mongoid.purge!

quake_data.each do |data|
    e = Earthquake.new()

    date = Date.new(data['YEAR'].to_i, (data['MONTH'] || 1).to_i, (data['DAY'] || 1).to_i)

    e.id = data['I_D']
    e.flag_tsunami = !!data['FLAG_TSUNAMI']
    e.date = date
    e.focal_depth = data['FOCAL_DEPTH'].to_i
    e.eq_primary = data['EQ_PRIMARY'].to_f
    e.intensity = data['INTENSITY'].to_i
    e.country = data['COUNTRY']
    e.state = data['STATE']
    e.location_name = data['LOCATION_NAME']
    e.latitude = data['LATITUDE'].to_f
    e.longitude = data['LONGITUDE'].to_f
    e.region_code = data['REGION_CODE'].to_i
    e.deaths = (data['TOTAL_DEATHS'] || data['DEATHS']).to_i
    e.missing = (data['TOTAL_MISSING'] || data['MISSING']).to_i
    e.injuries = (data['TOTAL_INJURIES'] || data['INJURIES']).to_i
    e.damage_millions_dollars = (data['TOTAL_DAMAGE_MILLIONS_DOLLARS'] || data['DAMAGE_MILLIONS_DOLLARS']).to_f
    e.houses_destroyed = (data['TOTAL_HOUSES_DESTROYED'] || data['HOUSES_DESTROYED']).to_i
    e.houses_damaged = (data['TOTAL_HOUSES_DAMAGED'] || data['HOUSES_DAMAGED']).to_i

    e.save!
end
