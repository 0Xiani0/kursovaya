package com.example.artofhistory.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.artofhistory.models.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private final Context context;
    private static final String
            DATABASE_NAME = "db_art_of_history_tasks",
            TABLE_NAME = "Tasks",
            COLUMN_ID = "id_task",
            COLUMN_NAME = "name",
            COLUMN_QUESTION = "question",
            COLUMN_ANSWER = "answer",
            COLUMN_IS_DONE = "is_done",
            COLUMN_LAST_ATTEMPT = "last_attempt";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID +         " INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME +       " TEXT NOT NULL UNIQUE," +
                COLUMN_QUESTION +   " TEXT NOT NULL UNIQUE," +
                COLUMN_ANSWER +     " TEXT NOT NULL," +
                COLUMN_LAST_ATTEMPT+" TEXT," +
                COLUMN_IS_DONE +    " INTEGER NOT NULL DEFAULT 0 CHECK (is_done IN (0, 1)))"
        );
        db.execSQL("INSERT INTO 'Tasks' ('name', 'question', 'answer')" +
                "VALUES" +
                "('Виды искусства', 'Искусство проектировать, строить?', 'скульптура')," +
                "('Графика', 'Произведение, в котором изображение нанесено на бумагу карандашом или тушью?', 'графика')," +
                "('Лепка', 'Изделия из глины, закрепленные обжигом?', 'керамика')," +
                "('Искусство', 'Объёмное художественное произведение, созданное путём резьбы, лепки, высекания, отливки.?', 'скульптура')," +
                "('Портрет', 'Погрудное изображение человека в скульптуре?', 'бюст')," +
                "('Живопись', 'Картина, на которой изображено море, морской вид?', 'марина')," +
                "('Герасимов', 'На картине Герасимова \"После дождя\" изображена беседка, на которой стоит стол, а на нём - вааза с сиренью. К какому жанру относится эта картина?', 'натюрморт')," +
                "('Батальный жанр', 'На картинах батального жанра может быть изображено...', 'сражение')," +
                "('Руский художник', 'Его называли \"певцом русского леса\"?', 'шишкин')," +
                "('Возрождение', 'Город - родина Возрождения?', 'флоренция')," +
                "('Создатель стиля', 'Кто считается создателем стиля барокко в скульптуре?', 'бернини')," +
                "('Цитата художника', 'Городом «желтого дьявола» М. Горький называет?', 'нью-йорк')," +
                "('Эпоха романтизма', 'Какой французский живописец эпохи романтизма был автором картины «Свобода, ведущая народ» (1830 г.)?', 'делакруа')," +
                "('Художник', 'Идеи английского Просвещения выразил в своей живописи и графике...', 'хогарт')," +
                "('Ренессанс', 'К какой знаменитой школе живописи относился итальянский художник эпохи Ренессанса Франческо дель Косса?', 'феррарская')," +
                "('Фрески', 'Автор фресок «Сотворение Адама», «Отделение света от тьмы»?', 'микеланджело')," +
                "('Памятник', 'В каком году во Франции была открыта пещера Ласко — один из самых значительных памятников позднепалеолитического искусства?', '1940')," +
                "('Влияние художника', 'На творчество Х. Риберы, Ф. Сурбарана и раннего Диего Веласкеса оказал влияние...', 'караваджо')," +
                "('Скульптор', 'Какой великий античный скульптор, по свидетельству Павсания, был создателем статуи «Гермеса с младенцем Дионисом» (около 330 г. до н. э.)?', 'пракситель')," +
                "('Школа живописи', 'П.П. Рубенс — глава какой школы живописи XVII в.?', 'фламандской')," +
                "('Первая фотография с воздуха', 'Кто в 1858 г, поднявшись над Парижем на воздушном шаре, сделал первую в истории фотографию с воздуха?', 'турнашон')," +
                "('Ведущая школа', 'Ведущей школой в изобразительном искусстве Италии эпохи «треченто» являлась...', 'сиенская')," +
                "('Представитель направления', 'Представителем какого направления в искусстве был немецкий живописец-график Георг Гросс?', 'экспрессионизм')," +
                "('Разорение театра', 'Причина разорения театра Е. Шабельской?', 'интрига с векселями')," +
                "('Развалины дворца', 'На каком острове находятся развалины Кносского дворца, считающегося мифическим лабиринтом Минотавра?', 'крит')," +
                "('Романское искусство', 'Романское искусство Западной Европы было преимущественно...', 'религиозным')," +
                "('Творчество художников', 'Творчество А. Бёклина, М.А. Врубеля, О. Родена связано с...', 'символизмом')," +
                "('Колористическое мастерство', 'Высокое колористическое мастерство свойственно живописи...', 'рокотова')," +
                "('Итог пленэра', 'Итог традиции пленэра — живописная техника...', 'импрессионизма')," +
                "('Реализм в живописи', 'Какой художник, крупнейший представитель барокко, считается основателем реализма в живописи?', 'караваджо')," +
                "('Общество искусства и литературы', 'Кто руководил обществом искусства и литературы?', 'станиславский')," +
                "('Пирамида', 'Какая египетская пирамида считается самой древней?', 'джосера')," +
                "('Капелла в Ватикане', 'Какой архитектор был автором проекта строительства Сикстинской капеллы в Ватикане?', 'де дольчи')," +
                "('Автор картин', 'Кто автор картин «Вакх и Ариадна», «Венера перед зеркалом», «Любовь земная и небесная», «Св. Себастьян»?', 'тициан')," +
                "('Здание в стиле ампир', 'В каком году по проекту К. Росси (в стиле ампир) было построено здание Александрийского театра в Санкт-Петербурге?', '1832')," +
                "('Семь чудес света', 'В эпоху античности появился знаменитый список самых величественных архитектурных сооружений в мире - \"Семь чудес света\". Какое из \"чудес\" было древнейшим?', 'пирамида хеопса')," +
                "('Статуя', 'Кого изображает единственная сохранившаяся с античности конная статуя, изготовленная в 176 г.?', 'марка аврелия')," +
                "('Собор Святого Бавона', 'Какое библейское событие является темой Гентского алтаря (авторы - братья Хуберт и Ян ван Эйк) в католическом кафедральном соборе Святого Бавона?', 'поклонение агнцу')," +
                "('Зарождение мозайки', 'Где зародилась мозайка?', 'месопотамия')," +
                "('Объединение', 'Мастера какого художественного обьединенияследовали принципам символизма?', 'голубая роза')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Task> readTasks() {
        String query = "SELECT * FROM " + TABLE_NAME;
        db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        List<Task> tasksList = new ArrayList<>();
        if (cursor == null) return tasksList;
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();

                task.setIdTask(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                task.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION)));
                task.setAnswer(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANSWER)));
                task.setLastAttempt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_ATTEMPT)));
                task.setIsDone(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) > 0);

                tasksList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasksList;
    }

    public void updateTaskIsDone(int id, boolean isDone) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_IS_DONE, isDone);

        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void updateTaskLastAttempt(int id, String lastAttempt) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LAST_ATTEMPT, lastAttempt);

        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }
}
