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

function openChatWindow(userId, fullname, avatar) {
    document.getElementById('side-chat-friend-name').innerText = fullname;
    document.getElementById('side-chat-window').style.display = 'block';
    var avatarImg = document.getElementById('side-chat-avatar');
    if (avatarImg) {
        avatarImg.src = avatar;
    }
}

function closeSideChatWindow() {
    // Close the side chat window
    document.getElementById('side-chat-window').style.display = 'none';
}

function toggleMainChatWindow() {
    // Toggle the main chat window between minimized and expanded states
    var chatWindow = document.getElementById('chat-window-container');
    chatWindow.classList.toggle('minimized');
    var icon = document.getElementById('chat-toggle-icon');
    icon.textContent = chatWindow.classList.contains('minimized') ? '+' : '-';
}

function sendSideMessage() {
    // Implement functionality to send a message from the side chat window
    var messageInput = document.getElementById('side-message-input').value;
    // You can handle sending the message logic here
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







