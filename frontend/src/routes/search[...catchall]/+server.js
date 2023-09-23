export async function GET(req) {
  const headers = { 'Content-Type': 'application/json'}
  const res = await fetch("http://localhost:3000/search" + req.params.catchall)
  const text = await res.text()
  return new Response(text, { headers })
}
