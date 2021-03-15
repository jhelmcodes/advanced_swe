export const BASE_URL = "http://localhost:8080";

export async function api(url, params = {}) {
  params = Object.assign({
    //mode: "cors",
    cache: "no-cache"
  }, params);

  params.headers = Object.assign({
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*"
  }, params.headers);

  let response = await fetch(BASE_URL + url, params);
  let json = (await response.json()) || {};
  if (!response.ok) {
    let errorMessage = json.error ? json.error.error || json.error : response.status;
    throw new Error(errorMessage);
  }
  return json;
}
