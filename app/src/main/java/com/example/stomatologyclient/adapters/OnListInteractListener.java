package com.example.stomatologyclient.adapters;

import com.example.stomatologyclient.models.NamedModel;

/**
 * Интерфейс для взаиодействия
 */
public interface OnListInteractListener
{
    /**
     * Нажата строка
     * @param id
     */
    void OnItemClick(int id);

    /**
     * Нажата кнопка удалить
     * @param id
     */
    void OnRemoveClick(int id);

    /**
     * Нажата кнопка редактировать
     * @param id
     */
    void OnEditClick(int id);
}