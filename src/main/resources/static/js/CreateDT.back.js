//ͨ�����ú�̨���񣬴��������鿴��DT
function creatTable(DTname, url) {
    //��������DT��ŵ�DTtable������������
    var DTtable = $(DTname).DataTable({
        //���ݴ���
        "ajax": {
            "url": url,
            "dataSrc": "data", //���ƴ����ֶΡ���������飬��ôdataSrcΪ""
        },
        //����ÿҳ��ʾ��������
        "lengthMenu": [15, 25, 50, 75, 100],
        //�����ؽ�
        "destroy": true,
        //���й���ʱ����������ˮƽ������
        "scrollX": true,
        //����������
        "columns": [
            {
                "data": "code",
                "title": "���",
            },
            {
                "data": "name",
                "title": "��������",
            },
            {
                "data": "enName",
                "title": "Ӣ������",
            },
            {
                "data": "createTime",
                "title": "����",
            },
            {
                "data": "createTime",
                "title": "����",
            },
            {
                "data": "createTime",
                "title": "��������",
            },
            {
                "data": "createTime",
                "title": "��",
            },
            {
                "data": "createTime",
                "title": "�Ƿ�ش�",
            },
            {
                "data": "createTime",
                "title": "�����ִ���",
            },
            {
                "data": "createTime",
                "title": "��ע",
            },
            {
                "data": "createTime",
                "title": "����",
            },
        ],

        //��������
        "language": {
            "url": "./js/DataTables.json"
        },

    });//���tables�������
    
    //����DT����
    return DTtable;
}

//ͨ���������ݣ����������鿴��DT
function creatTableByVar(DTname, dataset) {
    //��������DT��ŵ�DTtable������������
    var DTtable = $(DTname).DataTable({
        //���ݴ���
        "data": dataset,
        //����ÿҳ��ʾ��������
        "lengthMenu": [15, 25, 50, 75, 100],
        //�����ؽ�
        "destroy": true,
        //���й���ʱ����������ˮƽ������
        "scrollX": true,
        //����������
        "columns": [
            {
                "data": "comments",
                "title": "���",
            },
            {
                "data": "fieldName",
                "title": "��������",
            },
            {
                "data": "enName",
                "title": "Ӣ������",
            },
            {
                "data": "maxsize",
                "title": "����",
            },
            {
                "data": "maxsize",
                "title": "����",
            },
            {
                "data": "maxsize",
                "title": "��������",
            },
            {
                "data": "maxsize",
                "title": "��",
            },
            {
                "data": "maxsize",
                "title": "�Ƿ�ش�",
            },
            {
                "data": "maxsize",
                "title": "�����ִ���",
            },
            {
                "data": "maxsize",
                "title": "��עbeizhu",
            },
            {
                "data": "maxsize",
                "title": "����",
            },
        ],

        //��������
        "language": {
            "url": "./js/DataTables.json"
        },

    });//���tables�������
    
    //����DT����
    return DTtable;
}