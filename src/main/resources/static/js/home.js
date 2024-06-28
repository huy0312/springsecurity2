var userSettings = document.querySelector(".user-settings");
var darkBtn = document.getElementById("dark-button");
var LoadMoreBackground =document.querySelector(".btn-LoadMore");
function UserSettingToggle(){
    userSettings.classList.toggle("user-setting-showup-toggle");
}
// darkBtn.onclick = function(){
//     darkBtn.classList.toggle("dark-mode-on");
// }

function darkModeON(){
    darkBtn.classList.toggle("dark-mode-on");
    document.body.classList.toggle("dark-theme");
}


function toggleNotifications() {
    var dropdown = document.getElementById('notification-dropdown');
    dropdown.classList.toggle('show');
}

function displayFriendRequest(notification) {
    var notificationItems = document.getElementById('notification-items');
    var notificationCountElement = document.getElementById('notification-count');
    var count = parseInt(notificationCountElement.innerText);
    count++;
    notificationCountElement.innerText = count;

    var item = document.createElement('div');
    item.classList.add('notification-item');
    item.innerHTML = '<img src="' + notification.avatar + '" alt="Profile Picture">' +
        '<p>' + notification.fullname + ' sent you a friend request</p>';
    notificationItems.appendChild(item);
}
document.addEventListener('DOMContentLoaded', function() {
    const chatHeader = document.querySelector('.chat-header');
    const chatWindowContainer = document.querySelector('.chat-window-container');
    const chatContent = document.querySelector('.chat-content');

    chatHeader.addEventListener('click', function() {
        const chatToggleIcon = document.getElementById('chat-toggle-icon');
        chatWindowContainer.classList.toggle('expanded');
        chatContent.classList.toggle('expanded');
        if (chatContent.style.display === 'none' || chatContent.style.display === '') {
            chatContent.style.display = 'flex';
            chatToggleIcon.textContent = '-';
        } else {
            chatContent.style.display = 'none';
            chatToggleIcon.textContent = '+';
        }
    });
});

// Open chat window for selected friend
function openChatWindow(friendName, friendId) {
    document.getElementById('chat-friend-name').textContent = friendName;
    document.getElementById('chat-window-container').style.display = 'block';
    document.getElementById('chat-content').style.display = 'flex';
    document.getElementById('chat-toggle-icon').textContent = '-';
    // Load previous messages with friendId
    loadMessages(friendId);
}

// Load previous messages (this is a placeholder function)
function loadMessages(friendId) {
    const chatMessages = document.getElementById('chat-messages');
    chatMessages.innerHTML = ''; // Clear previous messages
    // Fetch and display previous messages with friendId
    // This should be replaced with actual logic to fetch messages from the server
}

// Send message function
function sendMessage() {
    const input = document.getElementById('chat-input');
    const message = input.value.trim();
    if (message === "") return;

    const messageElement = document.createElement("div");
    messageElement.classList.add("message", 'user1');  // Assuming user1 is sending the message
    messageElement.textContent = message;

    const chatMessages = document.getElementById('chat-messages');
    chatMessages.appendChild(messageElement);

    input.value = "";
    scrollToBottom('chat-messages');
}

// Scroll to bottom function
function scrollToBottom(id) {
    const element = document.getElementById(id);
    element.scrollTop = element.scrollHeight;
}

// Initialize chat window as minimized
document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById('chat-content').style.display = 'none';
    document.getElementById('chat-toggle-icon').textContent = '+';
});
function toggleNotifications() {
    const dropdown = document.getElementById('notification-dropdown');
    if (dropdown.style.display === 'none' || dropdown.style.display === '') {
        dropdown.style.display = 'block';
    } else {
        dropdown.style.display = 'none';
    }
}

function acceptFriendRequest(button) {
    var form = button.form;
    var formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to accept friend request.');
            }
            return response.text();
        })
        .then(data => {
            alert(data); // Hiển thị thông báo thành công
            // Cập nhật giao diện tại đây nếu cần
        })
        .catch(error => {
            console.error('Error accepting friend request:', error);
            alert('Failed to accept friend request.'); // Hiển thị thông báo lỗi
        });
}

function rejectFriendRequest(button) {
    var form = button.form;
    var formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to reject friend request.');
            }
            return response.text();
        })
        .then(data => {
            alert(data); // Hiển thị thông báo thành công
            // Cập nhật giao diện tại đây nếu cần
        })
        .catch(error => {
            console.error('Error rejecting friend request:', error);
            alert('Failed to reject friend request.'); // Hiển thị thông báo lỗi
        });
}

document.addEventListener('DOMContentLoaded', function() {
    var onlineLists = document.querySelectorAll('.online-list');
    for (var i = 3; i < onlineLists.length; i++) {
        onlineLists[i].style.display = 'none';
    }
});







