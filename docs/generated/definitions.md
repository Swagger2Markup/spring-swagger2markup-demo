## Definitions
### MailStorageQuotaResponse
|Name|Type|Required|
|----|----|----|
|mailStorageQuota|ref|false|


### JSON Schema
```json
{
  "type" : "object",
  "properties" : {
    "mailStorageQuota" : {
      "type" : "object",
      "properties" : {
        "quotaValueType" : {
          "type" : "string",
          "enum" : [ "DEFAULT", "CUSTOM" ]
        },
        "mailStorageQuotaValue" : {
          "type" : "string",
          "enum" : [ "THREEDAYS", "FOURTEENDAYS", "THIRTYDAYS", "NINETYDAYS", "INFINITE" ]
        }
      }
    }
  }
}
```

### XML Schema
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="mailStorageQuota" type="mailStorageQuota"/>

  <xs:element name="mailStorageQuotaResponse" type="mailStorageQuotaResponse"/>

  <xs:complexType name="mailStorageQuotaResponse">
    <xs:sequence>
      <xs:element ref="mailStorageQuota" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="mailStorageQuota" final="extension restriction">
    <xs:sequence>
      <xs:element name="mailStorageQuotaValue" type="mailStorageQuotaValue" minOccurs="0"/>
      <xs:element name="quotaValueType" type="quotaValueType" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>

  <xs:simpleType name="mailStorageQuotaValue">
    <xs:restriction base="xs:string">
      <xs:enumeration value="THREEDAYS"/>
      <xs:enumeration value="FOURTEENDAYS"/>
      <xs:enumeration value="THIRTYDAYS"/>
      <xs:enumeration value="NINETYDAYS"/>
      <xs:enumeration value="INFINITE"/>
    </xs:restriction>
  </xs:simpleType>

  <xs:simpleType name="quotaValueType">
    <xs:restriction base="xs:string">
      <xs:enumeration value="DEFAULT"/>
      <xs:enumeration value="CUSTOM"/>
    </xs:restriction>
  </xs:simpleType>
</xs:schema>
```

### MailStorageQuota
|Name|Type|Required|
|----|----|----|
|mailStorageQuotaValue|string|false|
|quotaValueType|string|false|


### JSON Schema
```json
{
  "type" : "object",
  "properties" : {
    "quotaValueType" : {
      "type" : "string",
      "enum" : [ "DEFAULT", "CUSTOM" ]
    },
    "mailStorageQuotaValue" : {
      "type" : "string",
      "enum" : [ "THREEDAYS", "FOURTEENDAYS", "THIRTYDAYS", "NINETYDAYS", "INFINITE" ]
    }
  }
}
```

### XML Schema
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="mailStorageQuota" type="mailStorageQuota"/>

  <xs:complexType name="mailStorageQuota" final="extension restriction">
    <xs:sequence>
      <xs:element name="mailStorageQuotaValue" type="mailStorageQuotaValue" minOccurs="0"/>
      <xs:element name="quotaValueType" type="quotaValueType" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>

  <xs:simpleType name="mailStorageQuotaValue">
    <xs:restriction base="xs:string">
      <xs:enumeration value="THREEDAYS"/>
      <xs:enumeration value="FOURTEENDAYS"/>
      <xs:enumeration value="THIRTYDAYS"/>
      <xs:enumeration value="NINETYDAYS"/>
      <xs:enumeration value="INFINITE"/>
    </xs:restriction>
  </xs:simpleType>

  <xs:simpleType name="quotaValueType">
    <xs:restriction base="xs:string">
      <xs:enumeration value="DEFAULT"/>
      <xs:enumeration value="CUSTOM"/>
    </xs:restriction>
  </xs:simpleType>
</xs:schema>
```

