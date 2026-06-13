import * as SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable()
export class WebSocketAPIService {
    webSocketEndPoint: string = environment.webScoketURL + "notification";
    stompClient: Client;
    topic: string = "/topic/messages";
    private messageSource = new BehaviorSubject("");
    currentMessage = this.messageSource.asObservable();

    constructor() {}

    _connect() {
        const ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = new Client({
            webSocketFactory: () => ws,
            onConnect: (frame) => {
                console.log('Connected: ' + frame);
                this.stompClient.subscribe(this.topic, notifications => {
                    this.onMessageReceived(notifications);
                });
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            }
        });

        this.stompClient.activate();
    }

    _disconnect() {
        if (this.stompClient) {
            this.stompClient.deactivate();
        }
        console.log("Disconnected");
    }

    errorCallBack(error) {
        console.log("errorCallBack -> " + error);
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

    _send(message) {
        console.log("Sending message via WebSocket");
        this.stompClient.publish({ destination: "/app/hello", body: JSON.stringify(message) });
    }

    onMessageReceived(message) {
        this.messageSource.next(JSON.parse(message.body));
    }
}
