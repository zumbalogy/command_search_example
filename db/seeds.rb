require('csv')
require('mongoid')

Mongoid.purge!

data_file = File.read(__dir__ + '/../earthquakes.csv')

quake_data = CSV.new(data_file, headers: true).map(&:to_hash)

lat_long_lookup = {
  'italy: sabines' => [42.469, 13.565],
  'italy: adriatic sea' => [43, 15],
  'italy: calabria' => [38.9, 16.6],
  'italy: ligurian coast' => [44.41, 8.932],
  'india: fort madran' => [13.03, 80.2787],
  'india: calcutta' => [22.5666, 88.3666],
  'china: sichuan province: changli' => [39.7166, 119.1666],
  'south coasts of asia minor' => [36.55, 32],
  'syrian coasts' => [35.35, 35.9166],
  'marmara sea' => [40.6866, 28.3186],
  'california: northern' => [38.76, -122.105],
  'hawaii' => [20.7, -156.987],
  'costa rica-panama' => [8.92, -82.71],
  'alaska: aleutian islands' => [53.888, -166.527],
  'solomon islands' => [-9, 159.5],
  'taiwan' => [23.88, 121],
  'micronesia, fed. states of: yap is, caroline is' => [7.58, 144.143],
  'new york: willetts point' => [40.76, -73.84],
  'el salvador: san marcos' => [13.58, -89.21],
  'el salvador: san salvador' => [13.69, -89.19],
  'united kingdom: ireland: sliabh-elpa' => [54.917, -8],
  'united kingdom: ireland' => [53.416, -8],
  'iceland' => [64.85, -18.53],
  'iceland: southern lowland' => [63.5, -18],
  'greece' => [39.4, 22.2],
  'greece: crete' => [35.22, 24.86],
  'greece: aegean sea' => [39, 25.26],
  'greece: maliakos gulf' => [38.8, 22.78],
  'egypt' => [26.71, 29.83],
  'canada: montreal' => [45.5, -73.6],
  'mexico: southern' => [17.2, -95],
  'peru: callao' => [-12, -77.1],
  'peru-chile' => [-18, -69.79],
  'indonesia: sumatra: mentawai islands' => [-2.13, 99.48],
  'algeria: bordjou, arreridj' => [36, 4.76],
  'turkey: cicilia, ceyhan (seyhan)' => [37, 35.8],
  'tonga islands' => [-19.23, -174.66],
  'new zealand: cook strait' => [-41.33, 174.33],
  'new zealand: wellington' => [-41.28, 174.77],
  'japan: ryukyu islands' => [26.5, 128],
  'guadeloupe' => [16.22, -61.57],
  'lara' => [10.19, -69.81]
}

quake_data.each do |data|
  e = Earthquake.new()

  date = Date.new(data['YEAR'].to_i, (data['MONTH'] || 1).to_i, (data['DAY'] || 1).to_i)

  e.id = data['I_D'].to_i
  e.tsu = !!data['FLAG_TSUNAMI']
  e.date = date
  e.focal_depth = data['FOCAL_DEPTH'].to_i
  e.eq_primary = data['EQ_PRIMARY'].to_f
  e.intensity = data['INTENSITY'].to_i
  e.country = (data['COUNTRY'] || '').downcase
  e.state = (data['STATE'] || '').downcase
  e.region = data['REGION_CODE'].to_i

  e.deaths = (data['TOTAL_DEATHS'] || data['DEATHS']).to_i
  e.missing = (data['TOTAL_MISSING'] || data['MISSING']).to_i
  e.injuries = (data['TOTAL_INJURIES'] || data['INJURIES']).to_i
  e.damage_millions_dollars = (data['TOTAL_DAMAGE_MILLIONS_DOLLARS'] || data['DAMAGE_MILLIONS_DOLLARS']).to_f
  e.houses_destroyed = (data['TOTAL_HOUSES_DESTROYED'] || data['HOUSES_DESTROYED']).to_i
  e.houses_damaged = (data['TOTAL_HOUSES_DAMAGED'] || data['HOUSES_DAMAGED']).to_i

  e.location = (data['LOCATION_NAME'] || '').sub(/^#{data['COUNTRY']}:/, '').strip
  e.location = e.location.gsub(/\s+/, ' ').downcase

  if data['LATITUDE'] == nil && data['LONGITUDE'] == nil
    key = data['LOCATION_NAME'].gsub(/\s+/, ' ')
    (lat, long) = lat_long_lookup[key]
    e.latitude = lat
    e.longitude = long
  else
    e.latitude = data['LATITUDE'].to_f
    e.longitude = data['LONGITUDE'].to_f
  end

  e.save!
end

q = Earthquake.where(location: 'grfece: off west coast').first
q.location = 'off west coast'
q.save!

File.open(__dir__ + '/../public/quake_export.json', 'w') do |f|
  f.write(Earthquake.all.to_json)
end

CSV.open(__dir__ + '/../public/quake_export.dsv', 'w', { col_sep: '|' }) do |dsv|
  attrs = Earthquake.attribute_names
  dsv << attrs
  Earthquake.all.each do |quake|
    dsv << attrs.map { |a| quake.send(a) }
  end
end

`gzip -f -k -9 #{__dir__ + '/../public/quake_export.json'}`
`gzip -f -k -9 #{__dir__ + '/../public/quake_export.dsv'}`
