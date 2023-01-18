const defaultUrl = window.location.origin;

async function post(path, body, headers = {}) {
  const url = `${defaultUrl}/${path}`;
  const options = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    body: JSON.stringify(body),
  };

  const res = await fetch(url, options);
  let data;

  try {
    data = await res.json();
  } catch (e) {
    data = res;
  }

  if (res.ok) {
    return data;
  } else {
    throw Error(data);
  }
}

async function get(path, body, headers = {}) {

  const url = `${defaultUrl}/${path}`;
  const options = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    body: JSON.stringify(body),
  };

  const res = await fetch(url, options);
  let data;

  try {
    data = await res.json();
  } catch (e) {
    data = res;
  }

  if (res.ok) {
    return data;
  } else {
    throw Error(data);
  }
}

async function Delete(path, body, headers = {}) {

  const url = `${defaultUrl}/${path}`;
  const options = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    body: JSON.stringify(body),
  };

  const res = await fetch(url, options);
  let data;

  try {
    data = await res.json();
  } catch (e) {
    data = res;
  }

  if (res.ok) {
    return data;
  } else {
    throw Error(data);
  }
}

async function put(path, body, headers = {}) {

  const url = `${defaultUrl}/${path}`;
  const options = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    body: JSON.stringify(body),
  };

  const res = await fetch(url, options);
  let data;

  try {
    data = await res.json();
  } catch (e) {
    data = res;
  }

  if (res.ok) {
    return data;
  } else {
    throw Error(data);
  }
}