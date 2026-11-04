const API = "http://localhost:8080/api";
const cards = document.querySelector("#cards");
const count = document.querySelector("#count");
const connection = document.querySelector("#connection");
const photos = [
 "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=900&q=80",
 "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=80",
 "https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=900&q=80",
 "https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=900&q=80",
 "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=900&q=80",
 "https://images.unsplash.com/photo-1560185008-b033106af5c3?auto=format&fit=crop&w=900&q=80"
];
const safe = value => String(value ?? "").replace(/[&<>"']/g, c => ({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
const kwacha = n => new Intl.NumberFormat("en-ZM",{maximumFractionDigits:0}).format(n);

function render(items) {
  count.textContent = `${items.length} ${items.length === 1 ? "home" : "homes"} found`;
  if (!items.length) { cards.innerHTML = '<p class="empty">No matching homes. Try a wider budget or another location.</p>'; return; }
  cards.innerHTML = items.map((x,i) => `<article class="card">
    <div class="card-photo" style="background-image:url('${photos[i % photos.length]}')"><span class="tag">${safe(x.type)}</span></div>
    <div class="card-body"><p class="place">⌖ ${safe(x.location)}</p><h3>${safe(x.title)}</h3>
    <p class="desc">${safe(x.description)}</p><div class="card-bottom">
    <div class="price">K${kwacha(x.price)}<small>per month · ZMW</small></div>
    <button class="contact" type="button" data-phone="${safe(x.phone)}" data-name="${safe(x.landlordName)}">Contact landlord</button>
    </div></div></article>`).join("");
  cards.querySelectorAll(".contact").forEach(btn => btn.addEventListener("click", () => alert(`Contact ${btn.dataset.name} on ${btn.dataset.phone}. Arrange a viewing and verify the property before paying.`)));
}
async function load() {
  const params = new URLSearchParams();
  const location = document.querySelector("#location").value.trim();
  const min = document.querySelector("#min").value, max = document.querySelector("#max").value, type = document.querySelector("#type").value;
  if(location) params.set("location",location); if(min) params.set("minPrice",min); if(max) params.set("maxPrice",max); if(type) params.set("type",type);
  try {
    const response = await fetch(`${API}/listings?${params}`);
    if(!response.ok) throw new Error("API request failed");
    render(await response.json()); connection.textContent = "Connected to CampusFinder Java service."; connection.style.color="#16834b";
  } catch {
    count.textContent = "Java service offline";
    cards.innerHTML = '<p class="empty">Start the Java server using the README instructions, then refresh this page.</p>';
    connection.textContent = "Cannot connect to localhost:8080."; connection.style.color="#a65b13";
  }
}
document.querySelector("#filters").addEventListener("submit", e => {e.preventDefault();load();});
document.querySelector("#new-listing").addEventListener("submit", async e => {
  e.preventDefault(); const form=e.currentTarget, message=document.querySelector("#form-message");
  try {
    const response=await fetch(`${API}/listings`,{method:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded"},body:new URLSearchParams(new FormData(form))});
    const result=await response.json(); if(!response.ok) throw new Error(result.error || "Could not submit listing");
    message.textContent="Property added successfully."; message.style.color="#16834b"; form.reset(); await load();
  } catch(err) {message.textContent=err.message+" Ensure the Java service is running.";message.style.color="#a65b13";}
});
load();
