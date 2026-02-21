document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('command-input');
    const output = document.getElementById('terminal-output');

    input.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            const topic = input.value.trim();

            if (topic === "") return;
            output.innerHTML += `<br><span style="color: #fff;">> ACCESSING DATA: ${topic}...</span>`;

            fetch(`/api/terminal/search?topic=${encodeURIComponent(topic)}`)
                .then(response => {
                    if (!response.ok) throw new Error("ACCESS DENIED: DATABASE OFFLINE");
                    return response.text();
                })
                .then(data => {
                    output.innerHTML += `<div><pre>${data}</pre></div>`;
                    input.value = ''; // Clear input
                })
                .catch(err => {
                    output.innerHTML += `<br><span style="color: #ff0000;">ERROR: ${err.message}</span>`;
                    input.value = '';
                });
        }
    });
    const scrollToBottom = () => {
        output.scrollTop = output.scrollHeight;
    };

    const observer = new MutationObserver(scrollToBottom);
    observer.observe(output, { childList: true, subtree: true });

    const initializeLoreScroll = () => {
        const loreContainer = document.querySelector('.bottom-lore');
        const scrollContent = document.querySelector('.scrolling-content');

        if (!scrollContent) return;
        const clone = scrollContent.cloneNode(true);
        loreContainer.appendChild(clone);
        const contentHeight = scrollContent.offsetHeight;
        const speed = 30;
        const duration = contentHeight / speed;

        scrollContent.style.animation = `scroll-up ${duration}s linear infinite`;
        clone.style.animation = `scroll-up ${duration}s linear infinite`;

        clone.style.animationDelay = `-${duration / 2}s`;
    };

    initializeLoreScroll();
});


window.clearTerminal = () => {
    document.getElementById('terminal-output').innerHTML = 'READY FOR INPUT...';
};