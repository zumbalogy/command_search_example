require('json')
require('csv')

data_file = File.read(__dir__ + '/../earthquakes.csv')

quake_data = CSV.new(data_file, headers: true).map(&:to_hash)

lat_long_lookup = {
  'ITALY: SABINES' => [42.469, 13.565],
  'ITALY: ADRIATIC SEA' => [43, 15],
  'ITALY: CALABRIA' => [38.9, 16.6],
  'ITALY: LIGURIAN COAST' => [44.41, 8.932],
  'INDIA: FORT MADRAN' => [13.03, 80.2787],
  'INDIA: CALCUTTA' => [22.5666, 88.3666],
  'CHINA: SICHUAN PROVINCE: CHANGLI' => [39.7166, 119.1666],
  'SOUTH COASTS OF ASIA MINOR' => [36.55, 32],
  'SYRIAN COASTS' => [35.35, 35.9166],
  'MARMARA SEA' => [40.6866, 28.3186],
  'CALIFORNIA: NORTHERN' => [38.76, -122.105],
  'HAWAII' => [20.7, -156.987],
  'COSTA RICA-PANAMA' => [8.92, -82.71],
  'ALASKA: ALEUTIAN ISLANDS' => [53.888, -166.527],
  'SOLOMON ISLANDS' => [-9, 159.5],
  'TAIWAN' => [23.88, 121],
  'MICRONESIA, FED. STATES OF: YAP IS, CAROLINE IS' => [7.58, 144.143],
  'NEW YORK: WILLETTS POINT' => [40.76, -73.84],
  'EL SALVADOR: SAN MARCOS' => [13.58, -89.21],
  'EL SALVADOR: SAN SALVADOR' => [13.69, -89.19],
  'UNITED KINGDOM: IRELAND: SLIABH-ELPA' => [54.917, -8],
  'UNITED KINGDOM: IRELAND' => [53.416, -8],
  'ICELAND' => [64.85, -18.53],
  'ICELAND: SOUTHERN LOWLAND' => [63.5, -18],
  'GREECE' => [39.4, 22.2],
  'GREECE: CRETE' => [35.22, 24.86],
  'GREECE: AEGEAN SEA' => [39, 25.26],
  'GREECE: MALIAKOS GULF' => [38.8, 22.78],
  'EGYPT' => [26.71, 29.83],
  'CANADA: MONTREAL' => [45.5, -73.6],
  'MEXICO: SOUTHERN' => [17.2, -95],
  'PERU: CALLAO' => [-12, -77.1],
  'PERU-CHILE' => [-18, -69.79],
  'INDONESIA: SUMATRA: MENTAWAI ISLANDS' => [-2.13, 99.48],
  'ALGERIA: BORDJOU, ARRERIDJ' => [36, 4.76],
  'TURKEY: CICILIA, CEYHAN (SEYHAN)' => [37, 35.8],
  'TONGA ISLANDS' => [-19.23, -174.66],
  'NEW ZEALAND: COOK STRAIT' => [-41.33, 174.33],
  'NEW ZEALAND: WELLINGTON' => [-41.28, 174.77],
  'JAPAN: RYUKYU ISLANDS' => [26.5, 128],
  'GUADELOUPE' => [16.22, -61.57],
  'LARA' => [10.19, -69.81]
}

location_renames = {
  'grfece: off west coast' => 'off west coast'
}

clean_data = quake_data.map do |data|
  out = {}

  date = Date.new(data['YEAR'].to_i, (data['MONTH'] || 1).to_i, (data['DAY'] || 1).to_i)

  out['id'] = data['I_D'].to_i
  out['tsu'] = !!data['FLAG_TSUNAMI']
  out['date'] = date
  out['focal_depth'] = data['FOCAL_DEPTH'].to_i
  out['eq_primary'] = data['EQ_PRIMARY'].to_f
  out['intensity'] = data['INTENSITY'].to_i
  out['country'] = (data['COUNTRY'] || '').downcase
  out['state'] = (data['STATE'] || '').downcase
  out['region'] = data['REGION_CODE'].to_i

  out['deaths'] = (data['TOTAL_DEATHS'] || data['DEATHS']).to_i
  out['missing'] = (data['TOTAL_MISSING'] || data['MISSING']).to_i
  out['injuries'] = (data['TOTAL_INJURIES'] || data['INJURIES']).to_i
  out['damage_millions_dollars'] = (data['TOTAL_DAMAGE_MILLIONS_DOLLARS'] || data['DAMAGE_MILLIONS_DOLLARS']).to_f
  out['houses_destroyed'] = (data['TOTAL_HOUSES_DESTROYED'] || data['HOUSES_DESTROYED']).to_i
  out['houses_damaged'] = (data['TOTAL_HOUSES_DAMAGED'] || data['HOUSES_DAMAGED']).to_i

  out['location'] = (data['LOCATION_NAME'] || '').sub(/^#{data['COUNTRY']}:/, '').strip
  out['location'] = out['location'].gsub(/\s+/, ' ').downcase
  out['location'] = location_renames[out['location']] || out['location']

  if data['LATITUDE'] == nil && data['LONGITUDE'] == nil

    key = data['LOCATION_NAME'].gsub(/\s+/, ' ')
    (lat, long) = lat_long_lookup[key]
    out['latitude'] = lat
    out['longitude'] = long
  else
    out['latitude'] = data['LATITUDE'].to_f
    out['longitude'] = data['LONGITUDE'].to_f
  end

  out
end

File.open(__dir__ + '/../public/quake_export.json', 'w') do |f|
  f.write(clean_data.to_json)
end

`gzip -f -k -9 #{__dir__ + '/../public/quake_export.json'}`
