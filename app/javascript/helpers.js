export const fastQuakeFilter = (ids, list) => {
  const output = []
  let list_index = 0
  ids.forEach((id, i) => {
    while (id !== list[list_index].id) {
      list_index += 1
    }
    list[list_index].scrollIdx = i
    output.push(list[list_index])
    list_index += 1
  })
  return output
}

export const longLatFinder = (list) => {
  const ttt = Date.now()
  let minLat = 90
  let maxLat = -90
  let minLong = 180
  let maxLong = -180
  list.forEach((item) => {
    if (item.latitude < minLat) { minLat = item.latitude }
    if (item.latitude > maxLat) { maxLat = item.latitude }
    if (item.longitude < minLong) { minLong = item.longitude }
    if (item.longitude > maxLong) { maxLong = item.longitude }
  })
  return [minLat, maxLat, minLong, maxLong]
}

export const b64EncodeUnicode = (str) => {
  return btoa(
    encodeURIComponent(str)
    .replace(
      /%([0-9A-F]{2})/g,
      (match, p1) => { return String.fromCharCode('0x' + p1) }
    )
  )
}

export const b64DecodeUnicode = (str) => {
  return decodeURIComponent(
    atob(str)
    .split('')
    .map(c => { return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2) })
    .join(''))
}
