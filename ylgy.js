var body = $response.body
var url = $request.url

if (body) {
  var obj = JSON.parse($response.body)
  obj.data.daily_count =
    '10086'

  $done({ body: JSON.stringify(obj) })
} else {
  $done({})
}
